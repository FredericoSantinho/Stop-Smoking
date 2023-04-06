package neuro.stop.smoking.domain.repository

import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

interface ObserveSmokedCigarettesRepository {
	/**
	 * Observes all smoked cigarettes within a given period (inclusive).
	 *
	 * @param initialTimestamp initial timestamp (inclusive).
	 * @param finalTimestamp final timestamp (inclusive).
	 *
	 * @return a Flow that will emit a list with all smoked cigarettes within the given period every
	 * time it changes.
	 */
	fun observeSmokedCigarettes(
		initialTimestamp: Long = Long.MIN_VALUE,
		finalTimestamp: Long = Long.MAX_VALUE
	): Flow<List<SmokedCigaretteDto>>
}