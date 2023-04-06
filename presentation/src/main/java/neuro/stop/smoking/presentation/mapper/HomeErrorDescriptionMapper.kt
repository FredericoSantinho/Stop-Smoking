package neuro.stop.smoking.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.viewmodel.home.HomeUiState

@Composable
fun HomeUiState.ErrorDescription.toUi(): String {
	return when (this) {
		HomeUiState.ErrorDescription.ErrorRemovingSmokedCigarette -> stringResource(id = R.string.error_removing_smoked_cigarette)
		HomeUiState.ErrorDescription.ErrorLoadingData -> stringResource(id = R.string.error_loading_data)
		HomeUiState.ErrorDescription.ErrorSavingSmokedCigarette -> stringResource(id = R.string.error_saving_smoked_cigarette)
		HomeUiState.ErrorDescription.ErrorUpdatingLastCigaretteTime -> stringResource(id = R.string.error_updating_last_cigarette_time)
	}
}

@Composable
fun List<HomeUiState.ErrorDescription>.toUi(): List<String> {
	return map { it.toUi() }
}