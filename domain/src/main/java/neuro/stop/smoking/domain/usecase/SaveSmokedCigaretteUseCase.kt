package neuro.stop.smoking.domain.usecase

interface SaveSmokedCigaretteUseCase {
	/**
	 * Save a smoked cigarette.
	 *
	 * @param timestamp timestamp.
	 */
	suspend fun saveSmokedCigaretteUseCase(timestamp: Long)
}

/**
 * Exception thrown when save smoked cigarette fails.
 */
class SaveSmokedCigaretteError : java.lang.IllegalArgumentException() {
	override fun equals(other: Any?): Boolean {
		return other is SaveSmokedCigaretteError
	}

	override fun hashCode(): Int {
		return SaveSmokedCigaretteError::class.toString().hashCode()
	}
}