package neuro.stop.smoking.presentation.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

class SettingsActivity : FragmentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			StopSmokingTheme {
				SettingsComposable()
			}
		}
	}

	companion object {
		@JvmStatic
		fun start(context: Context) {
			val starter = Intent(context, SettingsActivity::class.java)
			context.startActivity(starter)
		}
	}
}