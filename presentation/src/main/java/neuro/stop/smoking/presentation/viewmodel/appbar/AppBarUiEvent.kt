package neuro.stop.smoking.presentation.viewmodel.appbar

import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarUiEvent.UiEvent
import neuro.stop.smoking.presentation.viewmodel.common.BaseUiEvent

class AppBarUiEvent : BaseUiEvent<UiEvent>() {
	fun navigateToSettings() {
		_uiEvent.value = UiEvent.NavigateToSettings()
	}

	fun focusSearch() {
		_uiEvent.value = UiEvent.FocusSearch()
	}

	sealed class UiEvent {
		class NavigateToSettings : UiEvent()
		class FocusSearch : UiEvent()
	}
}