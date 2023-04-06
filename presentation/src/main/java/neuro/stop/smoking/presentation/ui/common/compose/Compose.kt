package neuro.stop.smoking.presentation.ui.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberUnit(runnable: () -> Unit) {
	remember {
		runnable()
		0
	}
}