package neuro.stop.smoking.presentation.viewmodel.home

import androidx.compose.runtime.State
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

interface HomeViewModel {
	val uiState: State<HomeUiState.UiState>

	val cigaretteUrl: State<String>

	val title: State<Title>
	val smokedCigarettes: State<List<SmokedCigaretteModel>>
	val smokedCigarettesNumber: State<String>
	val lastCigaretteTimeMinutes: State<String>
	val projectionCigarettes: State<String>
	val projectionCost: State<String>

	fun onComposition()
	fun onRemoveCigaretteClick(smokedCigaretteId: Long)
	fun onRemoveCigaretteConfirmationClick(smokedCigaretteId: Long)
	fun onRemoveCigaretteConfirmationDismiss()
	fun onSmokeButtonClick()
	fun onErrorDismiss()
	fun onErrorOkButtonClick()
}

object HomeTitle : Title()