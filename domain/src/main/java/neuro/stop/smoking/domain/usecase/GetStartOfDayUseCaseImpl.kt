package neuro.stop.smoking.domain.usecase

import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.GetStartOfDayRepository
import java.util.Calendar
import java.util.Date

class GetStartOfDayUseCaseImpl(private val getStartOfDayRepository: GetStartOfDayRepository) :
	GetStartOfDayUseCase {

	override suspend fun getStartOfDay(calendar: Calendar): Calendar {
		val newCalendar = Calendar.getInstance(calendar.timeZone)
		val startOfDay = getStartOfDayRepository.getStartOfDay() ?: StartOfDayDto(0, 0)

		val hour = startOfDay.hour
		val minute = startOfDay.minute
		val day = calendar.get(Calendar.DAY_OF_MONTH)
		val month = calendar.get(Calendar.MONTH)
		val year = calendar.get(Calendar.YEAR)
		newCalendar.time = Date(0)

		newCalendar.set(year, month, day, hour, minute, 0)

		return newCalendar
	}
}