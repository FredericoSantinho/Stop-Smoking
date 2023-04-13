package neuro.stop.smoking.presentation.viewmodel.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState

class SettingsViewModelImpl : ViewModel(), SettingsViewModel {

	private val _uiEvent = mutableStateOf<UiEvent?>(null)
	override val uiEvent = _uiEvent.asState()

	override val title: State<Title> = mutableStateOf(SettingsTitle)

	override fun onBackClick() {
		_uiEvent.value = UiEvent.NavigateBack
	}

	override fun onChangeStartOfDayClick() {
		_uiEvent.value = UiEvent.NavigateToChangeStartOfDay
	}

	override fun eventConsumed() {
		_uiEvent.value = null
	}
}

