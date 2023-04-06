package neuro.stop.smoking.presentation.mapper

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel

interface SmokedCigaretteModelMapper {
	fun toPresentation(smokedCigaretteDto: SmokedCigaretteDto): SmokedCigaretteModel
	fun toPresentation(smokedCigaretteDtoList: List<SmokedCigaretteDto>): List<SmokedCigaretteModel>
}