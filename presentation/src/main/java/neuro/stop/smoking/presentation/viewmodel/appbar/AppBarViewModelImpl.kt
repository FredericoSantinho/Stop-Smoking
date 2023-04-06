package neuro.stop.smoking.presentation.viewmodel.appbar

import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.viewmodel.common.asState

class AppBarViewModelImpl : AppBarViewModel {
	override val title = mutableStateOf<Title>(Title.EmptyTitle)

	private val _uiEvent = mutableStateOf<UiEvent?>(null)
	override val uiEvent = _uiEvent.asState()

	override fun onSettingsButtonClick() {
		_uiEvent.value = UiEvent.NavigateToSettings
	}

	override fun onNavigateUpClick() {
		_uiEvent.value = UiEvent.NavigateUp
	}

	override fun eventConsumed() {
		_uiEvent.value = null
	}
}

sealed class UiEvent {
	object NavigateToSettings : UiEvent()
	object NavigateUp : UiEvent()
}

abstract class Title {
	object EmptyTitle : Title()
}