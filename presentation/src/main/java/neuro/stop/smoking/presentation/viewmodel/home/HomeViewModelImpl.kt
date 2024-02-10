package neuro.stop.smoking.presentation.viewmodel.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import neuro.expenses.register.viewmodel.common.formatter.DecimalFormatter
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.usecase.GetCurrentTimeMillisUseCase
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCase
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapper
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelImpl(
	private val observeSmokedCigarettesUseCase: ObserveSmokedCigarettesUseCase,
	private val removeSmokedCigaretteUseCase: RemoveSmokedCigaretteUseCase,
	private val saveSmokedCigaretteUseCase: SaveSmokedCigaretteUseCase,
	private val observeStartOfCurrentDayUseCase: ObserveStartOfCurrentDayUseCase,
	private val getCurrentTimeMillisUseCase: GetCurrentTimeMillisUseCase,
	private val smokedCigaretteModelMapper: SmokedCigaretteModelMapper,
	private val decimalFormatter: DecimalFormatter,
	private val appBarViewModel: AppBarViewModel,
	private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), HomeViewModel {

	private val _uiState = HomeUiState()
	override val uiState = _uiState.uiState

	override val cigaretteUrl: State<String> =
		mutableStateOf("https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg")

	override val title: State<Title> = mutableStateOf(HomeTitle)
	private val _smokedCigarettes = mutableStateOf(emptyList<SmokedCigaretteModel>())
	override val smokedCigarettes = _smokedCigarettes.asState()
	private val _smokedCigarettesNumber = mutableStateOf("")
	override val smokedCigarettesNumber = _smokedCigarettesNumber.asState()
	private val _lastCigaretteTimeMinutes = mutableStateOf("")
	override val lastCigaretteTimeMinutes = _lastCigaretteTimeMinutes.asState()
	private val _projectionCigarettes = mutableStateOf("")
	override val projectionCigarettes = _projectionCigarettes.asState()
	private val _projectionCost = mutableStateOf("")
	override val projectionCost = _projectionCost.asState()

	init {
		viewModelScope.launch {
			observeSmokedCigarettes().collect()
		}
	}

	override fun onComposition() {
		appBarViewModel.title.value = title.value
	}

	override fun onRemoveCigaretteClick(smokedCigaretteId: Long) {
		_uiState.showRemoveCigaretteConfirmation(smokedCigaretteId)
	}

	override fun onRemoveCigaretteConfirmationClick(smokedCigaretteId: Long) {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> _uiState.showErrorRemovingSmokedCigarette() }) {
			launch(coroutineDispatcher) {
				removeSmokedCigarette(smokedCigaretteId)
				_uiState.ready()
			}
		}
	}

	override fun onRemoveCigaretteConfirmationDismiss() {
		_uiState.ready()
	}

	private suspend fun removeSmokedCigarette(smokedCigaretteId: Long) {
		removeSmokedCigaretteUseCase.removeSmokedCigarette(smokedCigaretteId)
	}

	override fun onSmokeButtonClick() {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> _uiState.showErrorSavingSmokedCigarette() }) {
			launch(coroutineDispatcher) {
				saveSmokedCigaretteUseCase.saveSmokedCigaretteUseCase(getCurrentTimeMillisUseCase.getCurrentTimeMillis())
			}
		}
	}

	override fun onErrorDismiss() {
		_uiState.ready()
	}

	override fun onErrorOkButtonClick() {
		_uiState.ready()
	}

	private suspend fun observeSmokedCigarettes(): Flow<List<SmokedCigaretteDto>> {
		return flow {
			while (true) {
				emit(Unit)
				delay(calculateChangeOfDayDelay())
			}
		}.flatMapLatest {
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay()
				.flatMapLatest {
					val startOfDayMillis = it.timeInMillis
					val initialTimestamp =
						if (getCurrentTimeMillisUseCase.getCurrentTimeMillis() > startOfDayMillis) startOfDayMillis else it.decrementOneDay().timeInMillis
					observeSmokedCigarettesUseCase.observeSmokedCigarettes(initialTimestamp, Long.MAX_VALUE)
				}
		}.flowOn(coroutineDispatcher).onEach {
			_smokedCigarettes.value = smokedCigaretteModelMapper.toPresentation(it)
			_smokedCigarettesNumber.value = it.size.toString()
			if (it.isNotEmpty()) {
				_lastCigaretteTimeMinutes.value = getLastCigaretteTimeMinutes(it[0])
				setLastCigaretteTimeIncrementer()
				setEstimations(it)
			} else {
				_lastCigaretteTimeMinutes.value = ""
			}
		}
			.catch { _uiState.showErrorLoadingData() }
	}

	private fun setEstimations(smokedCigaretteDtos: List<SmokedCigaretteDto>) {
		val projectionCigarettes = if (smokedCigaretteDtos.size > 1) {
			(smokedCigaretteDtos.size.toDouble()) / delayBetweenFirstAndLastCigarette(
				smokedCigaretteDtos
			) * HOUR_MILLIS * 14
		} else {
			0.0
		}
		val projectedCost = projectionCigarettes * 5.2 / 20

		_projectionCigarettes.value = projectionCigarettes.toInt().toString()
		_projectionCost.value = getDayMonthYearProjectionsString(projectedCost)
	}

	private fun getDayMonthYearProjectionsString(projectedCost: Double): String {
		val day = decimalFormatter.format(projectedCost) + " €"
		val month = decimalFormatter.format(projectedCost * 30) + " €"
		val year = decimalFormatter.format(projectedCost * 365) + " €"

		return "$day | $month | $year"
	}

	private fun delayBetweenFirstAndLastCigarette(smokedCigaretteDtos: List<SmokedCigaretteDto>): Long =
		smokedCigaretteDtos[0].timestamp - smokedCigaretteDtos[smokedCigaretteDtos.size - 1].timestamp

	private suspend fun calculateChangeOfDayDelay(): Long {
		return observeStartOfCurrentDayUseCase.observeStartOfCurrentDay().map {
			val nextDayCalendar = Calendar.getInstance(it.timeZone)
			nextDayCalendar.timeInMillis = it.timeInMillis + 24 * 60 * 60 * 1000
			val currentTimeInMillis = getCurrentTimeMillisUseCase.getCurrentTimeMillis()

			nextDayCalendar.timeInMillis - currentTimeInMillis
		}.first()
	}

	private fun Calendar.decrementOneDay(): Calendar {
		val timeInMillis = time.time
		val incrementedOneDay = timeInMillis - 24 * 60 * 60 * 1000
		val newCalendar = Calendar.getInstance(timeZone)
		newCalendar.time = Date(incrementedOneDay)

		return newCalendar
	}

	private fun setLastCigaretteTimeIncrementer() {
		viewModelScope.launch {
			flow {
				while (true) {
					delay(1000 * 60)
					if (lastCigaretteTimeMinutes.value.isNotEmpty()) {
						emit((lastCigaretteTimeMinutes.value.toLong() + 1).toString())
					}
				}
			}.flowOn(coroutineDispatcher).onEach { _lastCigaretteTimeMinutes.value = it }
				.catch { _uiState.showErrorUpdatingLastCigaretteTime() }
				.collect()
		}
	}

	private fun getLastCigaretteTimeMinutes(smokedCigaretteDto: SmokedCigaretteDto): String {
		val currentTimeMillis = getCurrentTimeMillisUseCase.getCurrentTimeMillis()
		val smokedCigaretteTimestamp = smokedCigaretteDto.timestamp
		val difference = currentTimeMillis - smokedCigaretteTimestamp

		val differenceInMinutes = difference / 1000 / 60

		return differenceInMinutes.toString()
	}

	companion object {
		const val HOUR_MILLIS = 60 * 60 * 1000
	}
}