package neuro.stop.smoking.presentation.ui.common.datetime

import android.content.Context

interface ShowTimePicker {
	fun showTimePicker(
		context: Context,
		hour: Int,
		minutes: Int,
		onSetTime: (hour: Int, minute: Int) -> Unit
	)
}
