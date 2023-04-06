package neuro.stop.smoking.domain.repository

interface NewSmokedCigaretteIdRepository {
	/**
	 * Generate a new smoked cigarette id.
	 *
	 * The id returned must be consumed (ie, saved) in order to generate a new one. This means
	 * repeated calls to this method will return the same value if it is not consumed between them.
	 *
	 * @return a new smoked cigarette id.
	 */
	suspend fun newSmokedCigaretteId(): Long
}