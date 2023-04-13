package neuro.stop.smoking.presentation.viewmodel.settings

import androidx.compose.runtime.State
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

interface SettingsViewModel {
	val uiEvent: State<UiEvent?>

	val title: State<Title>

	fun onBackClick()
	fun onChangeStartOfDayClick()
	fun eventConsumed()
}

sealed class UiEvent {
	object NavigateBack : UiEvent()
	object NavigateToChangeStartOfDay : UiEvent()
}

object SettingsTitle : Title()