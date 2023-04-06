package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.repository.ObserveSmokedCigarettesRepository

class ObserveSmokedCigarettesUseCaseImpl(
	private val observeSmokedCigarettesRepository: ObserveSmokedCigarettesRepository
) :
	ObserveSmokedCigarettesUseCase {

	override fun observeSmokedCigarettes(
		initialTimestamp: Long,
		finalTimestamp: Long
	): Flow<List<SmokedCigaretteDto>> {
		return observeSmokedCigarettesRepository.observeSmokedCigarettes(
			initialTimestamp,
			finalTimestamp
		)
	}
}