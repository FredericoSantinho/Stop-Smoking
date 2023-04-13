package neuro.stop.smoking.presentation.viewmodel.common.datetime

import neuro.stop.smoking.presentation.viewmodel.common.formatter.NumberFormater
import neuro.stop.smoking.presentation.viewmodel.common.formatter.NumberFormaterImpl


class TimeTextMapperImpl(private val numberFormater: NumberFormater = NumberFormaterImpl()) :
	TimeTextMapper {
	override fun map(hour: Int, minute: Int): String {
		return numberFormater.format(hour) + 'h' + numberFormater.format(minute)
	}
}