package neuro.stop.smoking.domain.test.mocks

import neuro.stop.smoking.domain.dto.StartOfDayDto

fun startOfDayDtoMock(
	hour: Int = 29,
	minute: Int = hour
): StartOfDayDto = StartOfDayDto(hour, minute)

fun startOfDayDtoMockList(n: Int = 3): List<StartOfDayDto> =
	IntRange(1, n).map { startOfDayDtoMock(it) }
