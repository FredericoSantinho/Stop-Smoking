package neuro.stop.smoking.domain.usecase

interface RemoveSmokedCigaretteUseCase {
	/**
	 * Remove a smoked cigarette.
	 *
	 * @param smokedCigaretteId smoked cigarette id to remove.
	 */
	suspend fun removeSmokedCigarette(smokedCigaretteId: Long)
}