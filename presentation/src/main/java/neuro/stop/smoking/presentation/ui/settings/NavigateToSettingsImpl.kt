package neuro.stop.smoking.presentation.ui.settings

import android.content.Context

class NavigateToSettingsImpl : NavigateToSettings {
	override fun navigateToSettings(context: Context) {
		SettingsActivity.start(context)
	}
}