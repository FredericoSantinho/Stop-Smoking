package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import java.util.Calendar
import java.util.Date

class ObserveDailySmokedCigarettesUseCaseImpl(
	private val observeSmokedCigarettesUseCase: ObserveSmokedCigarettesUseCase,
	private val getStartOfDayUseCase: GetStartOfDayUseCase,
	private val observeStartOfCurrentDayUseCase: ObserveStartOfCurrentDayUseCase
) :
	ObserveDailySmokedCigarettesUseCase {
	@OptIn(ExperimentalCoroutinesApi::class)
	override fun observeDailySmokedCigarettes(): Flow<Map<Calendar, List<SmokedCigaretteDto>>> {
		return observeStartOfCurrentDayUseCase.observeStartOfCurrentDay()
			.flatMapLatest { startOfCurrentDay ->
				observeSmokedCigarettesUseCase.observeSmokedCigarettes()
					.map { it.groupBy { mapToAppropriateCalendar(it, startOfCurrentDay) } }
			}
	}

	/**
	 * Subtracts a Calendar's hours and minutes in a SmokedCigaretteDto timestamp.
	 *
	 * @param smokedCigaretteDto SmokedCigaretteDto.
	 * @param startOfCurrentDay Calendar with the start of current day.
	 * @return a Calendar equal to the smokedCigaretteDto timestamp minus startOfCurrentDay hours and
	 * minutes.
	 */
	private suspend fun mapToAppropriateCalendar(
		smokedCigaretteDto: SmokedCigaretteDto,
		startOfCurrentDay: Calendar
	): Calendar {
		val calendar = Calendar.getInstance(startOfCurrentDay.timeZone)
		val cigaretteOffsetTimestamp =
			smokedCigaretteDto.timestamp - startOfCurrentDay.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 - startOfCurrentDay.get(
				Calendar.MINUTE
			) * 60 * 1000
		calendar.time = Date(cigaretteOffsetTimestamp)
		return getStartOfDayUseCase.getStartOfDay(calendar)
	}
}