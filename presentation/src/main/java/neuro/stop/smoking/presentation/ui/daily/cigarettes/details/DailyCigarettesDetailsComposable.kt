package neuro.stop.smoking.presentation.ui.daily.cigarettes.details

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.ui.appbar.SimpleTopAppBar
import neuro.stop.smoking.presentation.ui.appbar.toPresentation
import neuro.stop.smoking.presentation.ui.home.SmokedCigarettesComposable
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsViewModel
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DummyDailyCigarettesDetailsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun DailyCigarettesDetailsComposable(
	dailyCigarettesDetailsViewModel: DailyCigarettesDetailsViewModel = getViewModel<DailyCigarettesDetailsViewModelImpl>()
) {
	Column {
		SimpleTopAppBar(dailyCigarettesDetailsViewModel.title.value.toPresentation())

		SmokedCigarettesComposable(
			dailyCigarettesDetailsViewModel.smokedCigarettes,
			dailyCigarettesDetailsViewModel.cigaretteUrl
		) { smokedCigaretteId ->
			dailyCigarettesDetailsViewModel.onRemoveCigaretteClick(
				smokedCigaretteId
			)
		}
	}
}

@Preview
@Composable
fun PreviewDailyCigarettesComposable() {
	StopSmokingTheme {
		DailyCigarettesDetailsComposable(DummyDailyCigarettesDetailsViewModel())
	}
}
