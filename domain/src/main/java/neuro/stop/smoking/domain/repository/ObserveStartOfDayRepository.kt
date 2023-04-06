package neuro.stop.smoking.domain.repository

import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.domain.dto.StartOfDayDto

interface ObserveStartOfDayRepository {
	/**
	 * Observe start of day. In case no start of day is set, null is emitted.
	 *
	 * @return a flow that will emit the start of day each time it changes.
	 */
	fun observeStartOfDay(): Flow<StartOfDayDto?>
}