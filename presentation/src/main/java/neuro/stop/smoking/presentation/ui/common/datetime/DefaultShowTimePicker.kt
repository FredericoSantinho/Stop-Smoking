package neuro.stop.smoking.presentation.ui.common.datetime

import android.app.TimePickerDialog
import android.content.Context

class DefaultShowTimePicker : ShowTimePicker {
	override fun showTimePicker(
		context: Context,
		hour: Int,
		minutes: Int,
		onSetTime: (hour: Int, minute: Int) -> Unit
	) {
		val picker = TimePickerDialog(
			context,
			{ _, sHour, sMinute -> onSetTime(sHour, sMinute) }, hour, minutes, true
		)
		picker.show()
	}
}