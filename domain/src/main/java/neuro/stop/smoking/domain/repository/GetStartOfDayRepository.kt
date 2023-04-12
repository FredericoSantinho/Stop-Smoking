package neuro.stop.smoking.domain.repository

import neuro.stop.smoking.domain.dto.StartOfDayDto

interface GetStartOfDayRepository {
	/**
	 * Get start of day. In case no start of day is set, null will be returned.
	 *
	 * @return a StartOfDayDto with the start of day.
	 */
	suspend fun getStartOfDay(): StartOfDayDto?
}