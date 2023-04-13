package neuro.stop.smoking.presentation.viewmodel.common.datetime

interface TimeTextMapper {
	fun map(hour: Int, minute: Int): String
}