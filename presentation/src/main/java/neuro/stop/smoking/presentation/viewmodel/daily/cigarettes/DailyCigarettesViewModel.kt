package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes

import androidx.compose.runtime.State
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay

interface DailyCigarettesViewModel {
	val uiState: State<UiState>
	val uiEvent: State<UiEvent?>

	val title: State<Title>

	val smokedCigarettesPerDay: State<List<SmokedCigarettesPerDay>>

	fun onComposition()
	fun onSmokedCigarettesPerDayClick(date: String)
	fun eventConsumed()
}

sealed class UiEvent {
	data class NavigateToSmokedCigarettesInDayDetails(val date: String) : UiEvent()
}

sealed class UiState {
	object Ready : UiState()
	object ShowErrorLoadingData : UiState()
}

object DailyCigarettesTitle : Title()