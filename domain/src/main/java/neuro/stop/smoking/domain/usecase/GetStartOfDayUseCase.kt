package neuro.stop.smoking.domain.usecase

import java.util.Calendar

interface GetStartOfDayUseCase {
	/**
	 * Get start of day for a given Calendar. In case no start of day is set, it will default to 00:00.
	 *
	 * @param calendar base calendar to set start of day.
	 * @return a new Calendar with the current start of day.
	 */
	suspend fun getStartOfDay(calendar: Calendar): Calendar
}