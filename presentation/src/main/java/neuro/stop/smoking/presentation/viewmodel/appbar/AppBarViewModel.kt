package neuro.stop.smoking.presentation.viewmodel.appbar

import androidx.compose.runtime.mutableStateOf

class AppBarViewModel {
	val title = mutableStateOf<Title>(Title.EmptyTitle)

	private val _uiEvent = AppBarUiEvent()
	val uiEvent = _uiEvent.uiEvent

	fun onSettingsButton() {
		_uiEvent.navigateToSettings()
	}

	fun eventConsumed() {
		_uiEvent.eventConsumed()
	}
}

abstract class Title {
	object EmptyTitle : Title()
}