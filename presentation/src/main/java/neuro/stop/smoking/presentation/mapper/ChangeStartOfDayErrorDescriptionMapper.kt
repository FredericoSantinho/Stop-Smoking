package neuro.stop.smoking.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.UiState

@Composable
fun UiState.ErrorDescription.toUi(): String {
	return when (this) {
		UiState.ErrorDescription.ErrorGettingStartOfDay -> stringResource(id = R.string.error_getting_start_of_day)
		UiState.ErrorDescription.ErrorSettingStartOfDay -> stringResource(id = R.string.error_setting_start_of_day)
	}
}

@Composable
fun List<UiState.ErrorDescription>.toUi(): List<String> {
	return map { it.toUi() }
}