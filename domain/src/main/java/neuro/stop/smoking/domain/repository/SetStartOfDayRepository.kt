package neuro.stop.smoking.domain.repository

import neuro.stop.smoking.domain.dto.StartOfDayDto

interface SetStartOfDayRepository {
	/**
	 * Set start of day.
	 *
	 * @param startOfDayDto StartOfDayDto to set.
	 */
	suspend fun setStartOfDay(startOfDayDto: StartOfDayDto)
}