package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.ObserveStartOfDayRepository
import java.util.Calendar
import java.util.Date

class ObserveStartOfCurrentDayUseCaseImpl(private val observeStartOfDayRepository: ObserveStartOfDayRepository) :
	ObserveStartOfCurrentDayUseCase {
	override fun observeStartOfCurrentDay(calendar: Calendar): Flow<Calendar> {
		return observeStartOfDayRepository.observeStartOfDay().map { it ?: StartOfDayDto(0, 0) }
			.map { toStartOfDay(calendar, it) }
	}

	private fun toStartOfDay(calendar: Calendar, startOfDayDto: StartOfDayDto): Calendar {
		val newCalendar = Calendar.getInstance(calendar.timeZone)

		val hour = startOfDayDto.hour
		val minute = startOfDayDto.minute
		val day = calendar.get(Calendar.DAY_OF_MONTH)
		val month = calendar.get(Calendar.MONTH)
		val year = calendar.get(Calendar.YEAR)
		newCalendar.time = Date(0)

		newCalendar.set(year, month, day, hour, minute, 0)

		return newCalendar
	}

}