package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay

class DailyCigarettesViewModelMock : DailyCigarettesViewModel {
	override val uiState: State<UiState> = mutableStateOf(UiState.Ready)
	private val _uiEvent = mutableStateOf<UiEvent?>(null)
	override val uiEvent: State<UiEvent?> = _uiEvent.asState()

	override val title: State<Title> = mutableStateOf(DailyCigarettesTitle)

	override val smokedCigarettesPerDay: State<ImmutableList<SmokedCigarettesPerDay>> =
		mutableStateOf(buildSmokedCigarettesPerDayList().toImmutableList())

	override fun onComposition() {
	}

	override fun onSmokedCigarettesPerDayClick(date: String) {
		_uiEvent.value = UiEvent.NavigateToSmokedCigarettesInDayDetails(date)
	}

	override fun eventConsumed() {
	}

	private fun buildSmokedCigarettesPerDayList(): List<SmokedCigarettesPerDay> {
		return listOf(
			SmokedCigarettesPerDay("2023-5-19", smokedCigaretteDtoMockList()),
			SmokedCigarettesPerDay("2023-5-18", smokedCigaretteDtoMockList(5)),
			SmokedCigarettesPerDay("2023-5-17", smokedCigaretteDtoMockList(7))
		)
	}
}