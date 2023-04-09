package neuro.stop.smoking.data.test.mocks

import neuro.stop.smoking.data.model.RoomStartOfDay

fun roomStartOfDayMock(
	startOfDayId: Long = 1L,
	hour: Int = 29,
	minute: Int = hour
): RoomStartOfDay = RoomStartOfDay(startOfDayId, hour, minute)

fun roomStartOfDayMockList(n: Int = 3): List<RoomStartOfDay> =
	IntRange(1, n).map { roomStartOfDayMock(it.toLong(), it) }
