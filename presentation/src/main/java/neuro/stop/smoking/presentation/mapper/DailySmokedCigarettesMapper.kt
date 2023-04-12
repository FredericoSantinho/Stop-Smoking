package neuro.stop.smoking.presentation.mapper

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay
import java.util.Calendar

fun Map<Calendar, List<SmokedCigaretteDto>>.toPresentation(): List<SmokedCigarettesPerDay> {
	return map { SmokedCigarettesPerDay(getDate(it.key), it.value) }
}

fun getDate(calendar: Calendar): String {
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH) + 1
	val day = calendar.get(Calendar.DAY_OF_MONTH)

	return "${year}-${month}-${day}"
}

