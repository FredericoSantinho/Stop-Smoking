package neuro.stop.smoking.presentation.viewmodel.home

import androidx.compose.runtime.mutableStateOf
import neuro.stop.smoking.presentation.viewmodel.common.asState

class HomeUiState {
	private val _uiState = mutableStateOf<UiState>(UiState.Ready)
	val uiState = _uiState.asState()

	fun ready() {
		_uiState.value = UiState.Ready
	}

	fun showRemoveCigaretteConfirmation(smokedCigaretteId: Long) {
		_uiState.value = UiState.ShowRemoveCigaretteConfirmation(smokedCigaretteId)
	}

	fun showErrorRemovingSmokedCigarette() {
		val errorsList = getPreviousErrorsIfNeeded()

		errorsList.add(ErrorDescription.ErrorRemovingSmokedCigarette)

		_uiState.value = UiState.ShowError(errorsList)
	}

	fun showErrorLoadingData() {
		val errorsList = getPreviousErrorsIfNeeded()

		errorsList.add(ErrorDescription.ErrorLoadingData)

		_uiState.value = UiState.ShowError(errorsList)
	}

	fun showErrorUpdatingLastCigaretteTime() {
		val errorsList = getPreviousErrorsIfNeeded()

		errorsList.add(ErrorDescription.ErrorUpdatingLastCigaretteTime)

		_uiState.value = UiState.ShowError(errorsList)
	}

	fun showErrorSavingSmokedCigarette() {
		val errorsList = getPreviousErrorsIfNeeded()

		errorsList.add(ErrorDescription.ErrorSavingSmokedCigarette)

		_uiState.value = UiState.ShowError(errorsList)
	}

	private fun getPreviousErrorsIfNeeded(): MutableList<ErrorDescription> {
		val errorsList = mutableListOf<ErrorDescription>()
		if (uiState.value is UiState.ShowError) {
			errorsList.addAll((uiState.value as UiState.ShowError).errorsDescriptions)
		}
		return errorsList
	}

	sealed class UiState {
		object Ready : UiState()
		data class ShowRemoveCigaretteConfirmation(val smokedCigaretteId: Long) : UiState()
		data class ShowError(val errorsDescriptions: List<ErrorDescription>) : UiState()
	}

	sealed class ErrorDescription {
		object ErrorRemovingSmokedCigarette : ErrorDescription()
		object ErrorLoadingData : ErrorDescription()
		object ErrorUpdatingLastCigaretteTime : ErrorDescription()
		object ErrorSavingSmokedCigarette : ErrorDescription()
	}
}