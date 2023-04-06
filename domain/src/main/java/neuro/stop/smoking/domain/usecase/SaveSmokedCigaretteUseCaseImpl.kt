package neuro.stop.smoking.domain.usecase

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.repository.NewSmokedCigaretteIdRepository
import neuro.stop.smoking.domain.repository.SaveSmokedCigaretteRepository

class SaveSmokedCigaretteUseCaseImpl(
	private val newSmokedCigaretteIdRepository: NewSmokedCigaretteIdRepository,
	private val saveSmokedCigaretteRepository: SaveSmokedCigaretteRepository
) : SaveSmokedCigaretteUseCase {
	override suspend fun saveSmokedCigaretteUseCase(timestamp: Long) {
		val newSmokedCigaretteId = newSmokedCigaretteIdRepository.newSmokedCigaretteId()

		saveSmokedCigaretteRepository.saveSmokedCigarette(
			SmokedCigaretteDto(
				newSmokedCigaretteId, timestamp
			)
		)
	}
}