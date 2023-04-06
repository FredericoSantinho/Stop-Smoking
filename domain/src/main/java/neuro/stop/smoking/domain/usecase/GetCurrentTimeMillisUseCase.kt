package neuro.stop.smoking.domain.usecase

interface GetCurrentTimeMillisUseCase {
	/**
	 * Get current time in milliseconds.
	 *
	 * @return the current time in milliseconds.
	 */
	fun getCurrentTimeMillis(): Long
}