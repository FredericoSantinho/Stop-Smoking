package neuro.stop.smoking.presentation.viewmodel.settings.change.start.day

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

class DummyChangeStartOfDayViewModel : ChangeStartOfDayViewModel {
	override val uiState: State<UiState> = mutableStateOf(UiState.Ready)
	override val uiEvent: State<UiEvent?> = mutableStateOf(null)

	override val title: State<Title> = mutableStateOf(ChangeStartOfDayTitle)

	override val hour: State<Int> = mutableStateOf(10)
	override val minute: State<Int> = mutableStateOf(0)
	override val timeText: State<String> = mutableStateOf("00h00")

	override fun onTimeClick() {
	}

	override fun onTimeChange(hour: Int, minute: Int) {
	}

	override fun onErrorDismiss() {
	}

	override fun onErrorOkButtonClick() {
	}

	override fun onBackButtonClick() {
	}

	override fun eventConsumed() {
	}
}