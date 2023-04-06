package neuro.stop.smoking.presentation.mapper

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import java.util.Calendar
import java.util.Date

class SmokedCigaretteModelMapperImpl : SmokedCigaretteModelMapper {
	override fun toPresentation(smokedCigaretteDto: SmokedCigaretteDto): SmokedCigaretteModel {
		return SmokedCigaretteModel(smokedCigaretteDto.smokedCigaretteId, mapToTime(smokedCigaretteDto))
	}

	override fun toPresentation(smokedCigaretteDtoList: List<SmokedCigaretteDto>) =
		smokedCigaretteDtoList.map { toPresentation(it) }

	private fun mapToTime(smokedCigaretteDto: SmokedCigaretteDto): String {
		val calendar = Date(smokedCigaretteDto.timestamp).toCalendar()
		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)

		return "${format(hour)}h${format(minute)}"
	}

	private fun Date.toCalendar(): Calendar {
		val cal: Calendar = Calendar.getInstance()
		cal.time = this
		return cal
	}

	private fun format(number: Int) = number.toString().padStart(2, '0')

}
