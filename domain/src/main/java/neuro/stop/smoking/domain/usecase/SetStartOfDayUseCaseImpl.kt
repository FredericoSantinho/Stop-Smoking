package neuro.stop.smoking.domain.usecase

import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.SetStartOfDayRepository

class SetStartOfDayUseCaseImpl(private val setStartOfDayRepository: SetStartOfDayRepository) :
	SetStartOfDayUseCase {
	override suspend fun setStartOfDay(startOfDayDto: StartOfDayDto) {
		setStartOfDayRepository.setStartOfDay(startOfDayDto)
	}
}