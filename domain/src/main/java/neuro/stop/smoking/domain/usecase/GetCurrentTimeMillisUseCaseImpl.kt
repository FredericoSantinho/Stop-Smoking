package neuro.stop.smoking.domain.usecase

class GetCurrentTimeMillisUseCaseImpl : GetCurrentTimeMillisUseCase {
	override fun getCurrentTimeMillis(): Long {
		return System.currentTimeMillis()
	}
}