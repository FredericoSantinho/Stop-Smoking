package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import neuro.stop.smoking.domain.usecase.ObserveDailySmokedCigarettesUseCase
import neuro.stop.smoking.presentation.mapper.toPresentation
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.common.asState
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay

class DailyCigarettesViewModelImpl(
	private val observeDailySmokedCigarettesUseCase: ObserveDailySmokedCigarettesUseCase,
	private val appBarViewModel: AppBarViewModel,
	coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
	ViewModel(), DailyCigarettesViewModel {

	private val _uiState = mutableStateOf<UiState>(UiState.Ready)
	override val uiState = _uiState.asState()
	private val _uiEvent = mutableStateOf<UiEvent?>(null)
	override val uiEvent = _uiEvent.asState()

	override val title: State<Title> = mutableStateOf(DailyCigarettesTitle)

	private val _smokedCigarettesByDay = mutableStateOf(emptyList<SmokedCigarettesPerDay>())
	override val smokedCigarettesPerDay = _smokedCigarettesByDay.asState()

	init {
		viewModelScope.launch {
			observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes()
				.map { it.toPresentation() }
				.flowOn(coroutineDispatcher)
				.onEach { _smokedCigarettesByDay.value = it }
				.catch { _uiState.value = UiState.ShowErrorLoadingData }
				.collect()
		}
	}

	override fun onComposition() {
		appBarViewModel.title.value = title.value
	}

	override fun onSmokedCigarettesPerDayClick(date: String) {
		_uiEvent.value = UiEvent.NavigateToSmokedCigarettesInDayDetails(date)
	}

	override fun eventConsumed() {
		_uiEvent.value = null
	}
}