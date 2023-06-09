package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details

import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

interface DailyCigarettesDetailsViewModel {
	val uiState: State<UiState>

	val cigaretteUrl: State<String>

	val date: State<String>
	val title: State<Title>
	val smokedCigarettes: State<ImmutableList<SmokedCigaretteModel>>

	fun onRemoveCigaretteClick(smokedCigaretteId: Long)
}

sealed class UiState {
	object Ready : UiState()
	object ShowErrorRemovingSmokedCigarette : UiState()
	object ShowErrorLoadingSmokedCigarettes : UiState()
}

class DailyCigarettesDetailsTitle(val date: String) : Title()
