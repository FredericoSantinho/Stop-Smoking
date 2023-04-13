package neuro.stop.smoking.presentation.viewmodel.settings.change.start.day

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCase
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapper
import java.util.Calendar

class ChangeStartOfDayViewModelImpl(
	private val observeStartOfCurrentDayUseCase: ObserveStartOfCurrentDayUseCase,
	private val setStartOfDayUseCase: SetStartOfDayUseCase,
	private val timeTextMapper: TimeTextMapper,
	private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
	ChangeStartOfDayViewModel, ViewModel() {

	private val _uiState = mutableStateOf<UiState>(UiState.Ready)
	override val uiState = _uiState.asState()
	private val _uiEvent = mutableStateOf<UiEvent?>(null)
	override val uiEvent: State<UiEvent?> = _uiEvent.asState()

	override val title: State<Title> = mutableStateOf(ChangeStartOfDayTitle)

	private val _hour = mutableStateOf(0)
	override val hour = _hour.asState()
	private val _minute = mutableStateOf(0)
	override val minute = _minute.asState()
	private val _timeText = mutableStateOf(timeTextMapper.map(hour.value, minute.value))
	override val timeText = _timeText.asState()

	init {
		viewModelScope.launch {
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay().flowOn(coroutineDispatcher)
				.onEach {
					setHourAndMinute(it)
				}
				.catch { _uiState.value = UiState.Error(UiState.ErrorDescription.ErrorGettingStartOfDay) }
				.collect()
		}
	}

	override fun onTimeClick() {
		_uiState.value = UiState.ShowTimePicker
	}

	override fun onTimeChange(hour: Int, minute: Int) {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
			_uiState.value = UiState.Error(UiState.ErrorDescription.ErrorSettingStartOfDay)
		}) {
			launch(coroutineDispatcher) {
				setStartOfDayUseCase.setStartOfDay(StartOfDayDto(hour, minute))
				launch(Dispatchers.Main) {
					_uiState.value = UiState.Ready
				}
			}
		}
	}

	override fun onErrorDismiss() {
		_uiState.value = UiState.Ready
	}

	override fun onErrorOkButtonClick() {
		_uiState.value = UiState.Ready
	}

	override fun onBackButtonClick() {
		_uiEvent.value = UiEvent.NavigateBack
	}

	override fun eventConsumed() {
		_uiEvent.value = null
	}

	private fun setHourAndMinute(calendar: Calendar) {
		_hour.value = calendar.get(Calendar.HOUR_OF_DAY)
		_minute.value = calendar.get(Calendar.MINUTE)
		_timeText.value = timeTextMapper.map(hour.value, minute.value)
	}
}