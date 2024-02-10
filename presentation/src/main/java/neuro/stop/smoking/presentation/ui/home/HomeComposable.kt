package neuro.stop.smoking.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.mapper.toUi
import neuro.stop.smoking.presentation.ui.common.compose.rememberUnit
import neuro.stop.smoking.presentation.ui.common.dialog.ErrorDialog
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.ui.theme.feedbackNegative
import neuro.stop.smoking.presentation.viewmodel.home.DummyHomeViewModel
import neuro.stop.smoking.presentation.viewmodel.home.HomeUiState.UiState
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModel
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModelImpl
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeComposable(
	homeViewModel: HomeViewModel = getViewModel<HomeViewModelImpl>()
) {
	val uiState by homeViewModel.uiState

	rememberUnit { homeViewModel.onComposition() }

	Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
		SmokedCigarettesComposable(
			homeViewModel.smokedCigarettes,
			homeViewModel.cigaretteUrl,
			modifier = Modifier.weight(1.0f)
		) { smokedCigaretteId -> homeViewModel.onRemoveCigaretteClick(smokedCigaretteId) }
		HomeFooterComposable(
			homeViewModel.smokedCigarettesNumber,
			homeViewModel.lastCigaretteTimeMinutes,
			homeViewModel.projectionCigarettes,
			homeViewModel.projectionCost
		) { homeViewModel.onSmokeButtonClick() }
	}

	onUiState(uiState, homeViewModel)
}

@Composable
fun onUiState(uiState: UiState, viewModel: HomeViewModel) {
	when (uiState) {
		UiState.Ready -> {}
		is UiState.ShowError -> ErrorDialog(
			uiState.errorsDescriptions.toUi().joinToString { it },
			{ viewModel.onErrorDismiss() },
			{ viewModel.onErrorOkButtonClick() })

		is UiState.ShowRemoveCigaretteConfirmation -> {
			ShowRemoveCigaretteConfirmationDialog(viewModel, uiState)
		}
	}
}

@Composable
private fun ShowRemoveCigaretteConfirmationDialog(
	viewModel: HomeViewModel,
	uiState: UiState.ShowRemoveCigaretteConfirmation
) {
	AlertDialog(onDismissRequest = { },
		confirmButton = {
			TextButton(
				onClick = {
					viewModel.onRemoveCigaretteConfirmationClick(uiState.smokedCigaretteId)
				}) {
				Text(stringResource(id = R.string.confirm))
			}
		},
		dismissButton = {
			TextButton(
				onClick = {
					viewModel.onRemoveCigaretteConfirmationDismiss()
				}) {
				Text(stringResource(id = R.string.cancel), color = feedbackNegative)
			}
		},
		title = {
			Text(text = "Remove cigarette?")
		})
}

@Preview
@Composable
fun PreviewHomeComposable() {
	StopSmokingTheme {
		HomeComposable(DummyHomeViewModel())
	}
}
