package neuro.stop.smoking.presentation.ui.daily.cigarettes.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme


class DailyCigarettesDetailsActivity : FragmentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			StopSmokingTheme {
				DailyCigarettesDetailsComposable()
			}
		}
	}


	companion object {
		const val DATE = "date"

		@JvmStatic
		fun start(context: Context, date: String) {
			val starter = Intent(context, DailyCigarettesDetailsActivity::class.java)
				.putExtra(DATE, date)
			context.startActivity(starter)
		}
	}
}