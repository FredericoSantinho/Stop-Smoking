package neuro.stop.smoking.data.test.mocks

import neuro.stop.smoking.data.model.RoomSmokedCigarette

fun roomSmokedCigaretteMock(
	smokedCigaretteId: Long = 29L,
	timestamp: Long = smokedCigaretteId
): RoomSmokedCigarette = RoomSmokedCigarette(smokedCigaretteId, timestamp)

fun roomSmokedCigaretteMockList(n: Long = 3): List<RoomSmokedCigarette> =
	LongRange(1, n).map { roomSmokedCigaretteMock(it) }
