package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

interface ObserveSmokedCigarettesUseCase {
	/**
	 * Observe smoked cigarettes for a given period.
	 *
	 * @param initialTimestamp initial timestamp (inclusive).
	 * @param finalTimestamp final timestamp (inclusive).
	 * @return a flow that will emit all smoked cigarettes for the given period each time it changes.
	 */
	fun observeSmokedCigarettes(
		initialTimestamp: Long = Long.MIN_VALUE,
		finalTimestamp: Long = Long.MAX_VALUE
	): Flow<List<SmokedCigaretteDto>>
}