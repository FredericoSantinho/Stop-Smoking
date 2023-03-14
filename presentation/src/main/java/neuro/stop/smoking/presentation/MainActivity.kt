package neuro.stop.smoking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.ui.main.MainComposable
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			StopSmokingTheme {
				MainComposable()
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	StopSmokingTheme {
		MainComposable()
	}
}