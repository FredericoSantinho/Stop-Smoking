package neuro.stop.smoking.presentation.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

@Composable
fun HomeComposable() {
	Text(text = "Home Composable")
}

object HomeTitle : Title()

@Preview
@Composable
fun PreviewHomeComposable() {
	StopSmokingTheme {
		HomeComposable()
	}
}
