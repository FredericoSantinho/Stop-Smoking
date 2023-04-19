package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.repository.GetStartOfDayRepository
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals

class GetStartOfDayUseCaseImplTest {

	@Test
	fun test() = runTest {
		val getStartOfDayRepository = mock<GetStartOfDayRepository>()

		val getStartOfDayUseCase = GetStartOfDayUseCaseImpl(getStartOfDayRepository)

		whenever(getStartOfDayRepository.getStartOfDay()).thenReturn(startOfDayDtoMock(10, 0))

		val startOfDay = getStartOfDayUseCase.getStartOfDay(newCalendar())

		assertEquals(expectedStartOfDay().time, startOfDay.time)
	}

	@Test
	fun testNullStartOfDay() {
		val getStartOfDayRepository = mock<GetStartOfDayRepository>()

		val getStartOfDayUseCase = GetStartOfDayUseCaseImpl(getStartOfDayRepository)

		runBlocking {
			whenever(getStartOfDayRepository.getStartOfDay()).thenReturn(null)

			val startOfDay = getStartOfDayUseCase.getStartOfDay(newCalendar())

			assertEquals(expectedStartOfDay(0).time, startOfDay.time)
		}
	}

	private fun newCalendar(): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, 8, 0, 0)

		return calendar
	}

	private fun expectedStartOfDay(hour: Int = 10): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, hour, 0, 0)

		return calendar
	}
}