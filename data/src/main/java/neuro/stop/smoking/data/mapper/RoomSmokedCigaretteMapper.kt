package neuro.stop.smoking.data.mapper

import neuro.stop.smoking.data.model.RoomSmokedCigarette
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

fun RoomSmokedCigarette.toDomain(): SmokedCigaretteDto =
	SmokedCigaretteDto(smokedCigaretteId, timestamp)

fun List<RoomSmokedCigarette>.toDomain() = map { it.toDomain() }

fun SmokedCigaretteDto.toData(): RoomSmokedCigarette =
	RoomSmokedCigarette(smokedCigaretteId, timestamp)