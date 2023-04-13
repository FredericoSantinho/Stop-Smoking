package neuro.stop.smoking.presentation.ui.settings.items

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

class ChangeStartOfDayActivity : FragmentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			StopSmokingTheme {
				ChangeStartOfDayComposable()
			}
		}
	}

	companion object {
		@JvmStatic
		fun start(context: Context) {
			val starter = Intent(context, ChangeStartOfDayActivity::class.java)
			context.startActivity(starter)
		}
	}
}