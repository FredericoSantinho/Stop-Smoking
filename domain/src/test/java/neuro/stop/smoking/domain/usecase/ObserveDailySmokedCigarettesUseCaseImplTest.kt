package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMock
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals

class ObserveDailySmokedCigarettesUseCaseImplTest {

	private val smokedCigaretteDto0 = smokedCigaretteDtoMock(1, 2)
	private val smokedCigaretteDto1 = smokedCigaretteDtoMock(3, 4)
	private val smokedCigaretteDto2 = smokedCigaretteDtoMock(5, 6)

	@Test
	fun test() = runTest {
		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val getStartOfDayUseCase = mock<GetStartOfDayUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()

		val observeDailySmokedCigarettesUseCase = ObserveDailySmokedCigarettesUseCaseImpl(
			observeSmokedCigarettesUseCase,
			getStartOfDayUseCase,
			observeStartOfCurrentDayUseCase
		)

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newStartOfDayCalendar()) })
		whenever(observeSmokedCigarettesUseCase.observeSmokedCigarettes()).thenReturn(flow {
			emit(
				newSmokedCigaretteDtoList()
			)
		})
		whenever(getStartOfDayUseCase.getStartOfDay(newCalendar(2))).thenReturn(newCalendar(0))
		whenever(getStartOfDayUseCase.getStartOfDay(newCalendar(4))).thenReturn(newCalendar(3))
		whenever(getStartOfDayUseCase.getStartOfDay(newCalendar(6))).thenReturn(newCalendar(3))

		val firstDailySmokedCigarettes =
			observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes().first()

		val expectedDailySmokedCigarettes = mapOf(
			newCalendar(0) to listOf(smokedCigaretteDto0),
			newCalendar(3) to listOf(smokedCigaretteDto1, smokedCigaretteDto2)
		)

		assertEquals(expectedDailySmokedCigarettes, firstDailySmokedCigarettes)
	}

	private fun newSmokedCigaretteDtoList(): List<SmokedCigaretteDto> {
		return listOf(
			smokedCigaretteDto0,
			smokedCigaretteDto1,
			smokedCigaretteDto2
		)
	}

	private fun newCalendar(timestampMillis: Long): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(timestampMillis)

		return calendar
	}

	private fun newStartOfDayCalendar(): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, 0, 0, 0)

		return calendar
	}
}