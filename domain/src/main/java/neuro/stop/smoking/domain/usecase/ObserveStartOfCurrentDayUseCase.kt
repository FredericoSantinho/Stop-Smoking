package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface ObserveStartOfCurrentDayUseCase {
	/**
	 * Observe start of current day for the given Calendar. In case no start of day is set, it will
	 * default to 00:00.
	 *
	 * @param calendar base calendar as base for start of day, defaults for Calendar.getInstance().
	 * @return a flow that will emit a new Calendar with the current start of day set each time it
	 * changes.
	 */
	fun observeStartOfCurrentDay(calendar: Calendar = Calendar.getInstance()): Flow<Calendar>
}