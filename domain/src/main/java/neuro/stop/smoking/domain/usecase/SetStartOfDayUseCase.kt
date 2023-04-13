package neuro.stop.smoking.domain.usecase

import neuro.stop.smoking.domain.dto.StartOfDayDto

interface SetStartOfDayUseCase {
	/**
	 * Set start of day.
	 *
	 * @param startOfDayDto StartOfDayDto to set.
	 */
	suspend fun setStartOfDay(startOfDayDto: StartOfDayDto)
}