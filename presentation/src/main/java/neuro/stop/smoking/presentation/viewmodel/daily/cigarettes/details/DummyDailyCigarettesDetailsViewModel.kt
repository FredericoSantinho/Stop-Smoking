package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

class DummyDailyCigarettesDetailsViewModel : DailyCigarettesDetailsViewModel {
	override val uiState: State<UiState> = mutableStateOf(UiState.Ready)

	override val cigaretteUrl: State<String> = mutableStateOf("url")

	override val date: State<String> = mutableStateOf("2023-5-16")
	override val title: State<Title> = mutableStateOf(DailyCigarettesDetailsTitle(date.value))
	override val smokedCigarettes: State<List<SmokedCigaretteModel>> =
		mutableStateOf(createSmokedCigaretteModelList())

	override fun onRemoveCigaretteClick(smokedCigaretteId: Long) {
	}

	private fun createSmokedCigaretteModelList(): List<SmokedCigaretteModel> {
		return listOf(
			SmokedCigaretteModel(1, "14h43"),
			SmokedCigaretteModel(2, "14h00"),
			SmokedCigaretteModel(3, "13h12"),
			SmokedCigaretteModel(4, "12h34"),
			SmokedCigaretteModel(5, "12h13")
		)
	}
}