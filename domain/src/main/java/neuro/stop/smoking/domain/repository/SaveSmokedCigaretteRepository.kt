package neuro.stop.smoking.domain.repository

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

interface SaveSmokedCigaretteRepository {
	/**
	 * Save a smoked cigarette.
	 *
	 * @param smokedCigaretteDto SmokedCigaretteDto to save.
	 */
	suspend fun saveSmokedCigarette(smokedCigaretteDto: SmokedCigaretteDto): Long
}