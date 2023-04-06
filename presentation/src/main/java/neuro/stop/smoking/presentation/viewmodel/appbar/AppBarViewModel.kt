package neuro.stop.smoking.presentation.viewmodel.appbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

interface AppBarViewModel {
	val uiEvent: State<UiEvent?>

	val title: MutableState<Title>

	fun onSettingsButtonClick()
	fun onNavigateUpClick()
	fun eventConsumed()
}