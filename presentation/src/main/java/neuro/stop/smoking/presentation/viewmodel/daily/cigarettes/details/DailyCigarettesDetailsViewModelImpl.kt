package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapper
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.DailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class DailyCigarettesDetailsViewModelImpl(
	private val removeSmokedCigaretteUseCase: RemoveSmokedCigaretteUseCase,
	private val observeSmokedCigarettesUseCase: ObserveSmokedCigarettesUseCase,
	private val observeStartOfCurrentDayUseCase: ObserveStartOfCurrentDayUseCase,
	private val smokedCigaretteModelMapper: SmokedCigaretteModelMapper,
	private val savedStateHandle: SavedStateHandle,
	private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
	ViewModel(), DailyCigarettesDetailsViewModel {
	private val _uiState = mutableStateOf<UiState>(UiState.Ready)
	override val uiState = _uiState.asState()

	override val cigaretteUrl: State<String> =
		mutableStateOf("https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg")

	override val date: State<String> =
		mutableStateOf(savedStateHandle.get<String>(DailyCigarettesDetailsActivity.DATE)!!)
	override val title: State<Title> = mutableStateOf(DailyCigarettesDetailsTitle(date.value))

	private val _smokedCigarettes =
		mutableStateOf(emptyList<SmokedCigaretteModel>().toImmutableList())
	override val smokedCigarettes = _smokedCigarettes.asState()

	init {
		viewModelScope.launch {
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay()
				.flatMapLatest {
					val calendar =
						getCalendarFromDate(savedStateHandle.get<String>(DailyCigarettesDetailsActivity.DATE)!!)
					val startOfDay = it.setDate(calendar)
					val startOfDayPlusOneDay = startOfDay.incrementOneDay()

					observeSmokedCigarettesUseCase.observeSmokedCigarettes(
						startOfDay.timeInMillis,
						startOfDayPlusOneDay.timeInMillis
					)
				}.flowOn(coroutineDispatcher)
				.onEach {
					_smokedCigarettes.value = smokedCigaretteModelMapper.toPresentation(it).toImmutableList()
				}.catch {
					_uiState.value = UiState.ShowErrorLoadingSmokedCigarettes
				}.collect()
		}
	}

	/**
	 * Set this Calendar date with the provided Calendar one.
	 *
	 * @param calendar Calendar to use for date.
	 * @return a new Calendar with the time of this and the date of the provided Calendar.
	 */
	private fun Calendar.setDate(calendar: Calendar): Calendar {
		val hour = get(Calendar.HOUR_OF_DAY)
		val minute = get(Calendar.MINUTE)
		val day = calendar.get(Calendar.DAY_OF_MONTH)
		val month = calendar.get(Calendar.MONTH)
		val year = calendar.get(Calendar.YEAR)

		val newCalendar = Calendar.getInstance(timeZone)
		newCalendar.time = Date(0)
		newCalendar.set(year, month, day, hour, minute, 0)

		return newCalendar
	}

	private fun getCalendarFromDate(date: String): Calendar {
		val split = date.split("-")
		val year = split[0].toInt()
		val month = split[1].toInt() - 1
		val day = split[2].toInt()

		val calendar = Calendar.getInstance()
		calendar.time = Date(0)
		calendar.set(year, month, day)

		return calendar
	}

	private fun Calendar.incrementOneDay(): Calendar {
		val timeInMillis = time.time
		val incrementedOneDay = timeInMillis + 24 * 60 * 60 * 1000
		val newCalendar = Calendar.getInstance(timeZone)
		newCalendar.time = Date(incrementedOneDay)

		return newCalendar
	}

	override fun onRemoveCigaretteClick(smokedCigaretteId: Long) {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
			_uiState.value = UiState.ShowErrorRemovingSmokedCigarette
		}) {
			launch(coroutineDispatcher) {
				removeSmokedCigaretteUseCase.removeSmokedCigarette(smokedCigaretteId)
			}
		}
	}
}