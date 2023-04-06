package neuro.stop.smoking.presentation.viewmodel.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import neuro.stop.smoking.presentation.viewmodel.home.HomeUiState.UiState

class DummyHomeViewModel : HomeViewModel {
	private val _uiState: MutableState<UiState> = mutableStateOf(UiState.Ready)
	override val uiState: State<UiState> = _uiState.asState()
	override val cigaretteUrl: State<String> = mutableStateOf("url")
	override val title: State<Title> = mutableStateOf(HomeTitle)
	override val smokedCigarettes: State<List<SmokedCigaretteModel>> =
		mutableStateOf(createSmokedCigaretteModelList())
	override val smokedCigarettesNumber: State<String> = mutableStateOf("5")
	override val lastCigaretteTimeMinutes: State<String> = mutableStateOf("12m")

	override fun onComposition() {

	}

	override fun onRemoveCigaretteClick(smokedCigaretteId: Long) {

	}

	override fun onSmokeButtonClick() {

	}

	override fun onErrorDismiss() {

	}

	override fun onErrorOkButtonClick() {
	}

	private fun createSmokedCigaretteModelList(): List<SmokedCigaretteModel> {
		return listOf(
			SmokedCigaretteModel(1, "12h13"),
			SmokedCigaretteModel(2, "12h34"),
			SmokedCigaretteModel(3, "13h12"),
			SmokedCigaretteModel(4, "14h00"),
			SmokedCigaretteModel(5, "14h43")
		)
	}
}