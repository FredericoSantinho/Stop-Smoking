package neuro.stop.smoking.presentation.viewmodel.settings.change.start.day

import androidx.compose.runtime.State
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

interface ChangeStartOfDayViewModel {
	val uiState: State<UiState>
	val uiEvent: State<UiEvent?>
	val title: State<Title>

	val hour: State<Int>
	val minute: State<Int>
	val timeText: State<String>

	fun onTimeClick()
	fun onTimeChange(hour: Int, minute: Int)
	fun onErrorDismiss()
	fun onErrorOkButtonClick()
	fun onBackButtonClick()
	fun eventConsumed()
}

sealed class UiState {
	data object Ready : UiState()
	data object ShowTimePicker : UiState()
	data class Error(val errorDescription: ErrorDescription) : UiState()

	sealed class ErrorDescription {
		data object ErrorGettingStartOfDay : ErrorDescription()
		data object ErrorSettingStartOfDay : ErrorDescription()
	}
}

sealed class UiEvent {
	data object NavigateBack : UiEvent()
}

object ChangeStartOfDayTitle : Title()