package neuro.stop.smoking.presentation.ui.settings.items

import android.content.Context

class NavigateToChangeStartOfDayImpl : NavigateToChangeStartOfDay {
	override fun navigateToChangeStartOfDayActivity(context: Context) {
		ChangeStartOfDayActivity.start(context)
	}
}