package neuro.stop.smoking.domain.usecase

import neuro.stop.smoking.domain.repository.RemoveCigaretteRepository

class RemoveSmokedCigaretteUseCaseImpl(private val removeCigaretteRepository: RemoveCigaretteRepository) :
	RemoveSmokedCigaretteUseCase {
	override suspend fun removeSmokedCigarette(smokedCigaretteId: Long) {
		removeCigaretteRepository.removeCigarette(smokedCigaretteId)
	}
}