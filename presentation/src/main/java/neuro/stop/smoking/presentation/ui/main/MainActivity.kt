package neuro.stop.smoking.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
