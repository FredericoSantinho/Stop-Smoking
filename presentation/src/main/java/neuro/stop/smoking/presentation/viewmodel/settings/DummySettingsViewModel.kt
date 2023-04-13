package neuro.stop.smoking.presentation.viewmodel.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState

class DummySettingsViewModel : SettingsViewModel {
	private val _uiEvent: MutableState<UiEvent?> = mutableStateOf(null)
	override val uiEvent: State<UiEvent?> = _uiEvent.asState()

	override val title: State<Title> = mutableStateOf(SettingsTitle)

	override fun onBackClick() {
	}

	override fun onChangeStartOfDayClick() {
		_uiEvent.value = UiEvent.NavigateToChangeStartOfDay
	}

	override fun eventConsumed() {

	}
}