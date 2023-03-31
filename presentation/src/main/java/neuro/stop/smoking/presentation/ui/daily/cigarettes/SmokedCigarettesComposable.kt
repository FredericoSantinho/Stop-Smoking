package neuro.stop.smoking.presentation.ui.daily.cigarettes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

@Composable
fun DailyCigarettesComposable() {
	Text(text = "Daily Cigarettes")
}

object DailyCigarettesTitle : Title()

@Preview
@Composable
fun PreviewDailyCigarettesComposable() {
	StopSmokingTheme {
		DailyCigarettesComposable()
	}
}
