package neuro.stop.smoking.domain.repository

interface RemoveCigaretteRepository {
	/**
	 * Remove a smoked cigarette.
	 *
	 * @param smokedCigaretteId smoked cigarette id to remove.
	 */
	suspend fun removeCigarette(smokedCigaretteId: Long)
}