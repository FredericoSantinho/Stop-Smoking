package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.repository.ObserveStartOfDayRepository
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ObserveStartOfCurrentDayUseCaseImplTest {

	@Test
	fun testInterface() = runTest {
		val observeStartOfDayRepository = mock<ObserveStartOfDayRepository>()

		val observeStartOfCurrentDayUseCase: ObserveStartOfCurrentDayUseCase =
			ObserveStartOfCurrentDayUseCaseImpl(observeStartOfDayRepository)

		whenever(observeStartOfDayRepository.observeStartOfDay()).thenReturn(flow {
			emit(
				startOfDayDtoMock()
			)
		})

		val firstStartOfCurrentDay =
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay().first()

		// We cannot test for the right emission because it's dependent on the timezone.
		assertNotNull(firstStartOfCurrentDay)
	}

	@Test
	fun test() = runTest {
		val observeStartOfDayRepository = mock<ObserveStartOfDayRepository>()

		val observeStartOfCurrentDayUseCase =
			ObserveStartOfCurrentDayUseCaseImpl(observeStartOfDayRepository)

		whenever(observeStartOfDayRepository.observeStartOfDay()).thenReturn(flow {
			emit(
				startOfDayDtoMock(
					10,
					0
				)
			)
		})

		val firstStartOfCurrentDay =
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(newCalendar()).first()

		assertEquals(expectedStartOfCurrentDay(), firstStartOfCurrentDay)
	}

	@Test
	fun testNullStartOfDay() = runTest {
		val observeStartOfDayRepository = mock<ObserveStartOfDayRepository>()

		val observeStartOfCurrentDayUseCase =
			ObserveStartOfCurrentDayUseCaseImpl(observeStartOfDayRepository)

		whenever(observeStartOfDayRepository.observeStartOfDay()).thenReturn(flow {
			emit(null)
		})

		val firstStartOfCurrentDay =
			observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(newCalendar()).first()

		assertEquals(expectedStartOfCurrentDay(0), firstStartOfCurrentDay)
	}

	private fun newCalendar(): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, 8, 0, 0)

		return calendar
	}

	private fun expectedStartOfCurrentDay(hour: Int = 10): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, hour, 0, 0)

		return calendar
	}
}