package neuro.stop.smoking.domain.test.mocks

import neuro.stop.smoking.domain.dto.SmokedCigaretteDto

fun smokedCigaretteDtoMock(
	smokedCigaretteId: Long = 29L,
	timestamp: Long = smokedCigaretteId
): SmokedCigaretteDto = SmokedCigaretteDto(smokedCigaretteId, timestamp)

fun smokedCigaretteDtoMockList(n: Long = 3): List<SmokedCigaretteDto> =
	LongRange(1, n).map { smokedCigaretteDtoMock(it) }
