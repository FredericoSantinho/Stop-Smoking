package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import neuro.stop.smoking.domain.usecase.ObserveDailySmokedCigarettesUseCase
import neuro.stop.smoking.presentation.MainDispatcherRule
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DailyCigarettesViewModelImplTest {

	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Test
	fun testHappyPack() {
		val observeDailySmokedCigarettesUseCase = mock<ObserveDailySmokedCigarettesUseCase>()
		val appBarViewModel = mock<AppBarViewModel>()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes()).thenReturn(flow {
			emit(
				buildDailySmokedCigarettesMap()
			)
		})

		val dailyCigarettesViewModel =
			DailyCigarettesViewModelImpl(
				observeDailySmokedCigarettesUseCase,
				appBarViewModel,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			buildExpectedSmokedCigarettesPerDay(),
			dailyCigarettesViewModel.smokedCigarettesPerDay.value
		)
	}

	@Test
	fun testErrorObservingDailySmokedCigarettes() {
		val observeDailySmokedCigarettesUseCase = mock<ObserveDailySmokedCigarettesUseCase>()
		val appBarViewModel = mock<AppBarViewModel>()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes()).thenReturn(flow {
			error("")
		})

		val dailyCigarettesViewModel =
			DailyCigarettesViewModelImpl(
				observeDailySmokedCigarettesUseCase,
				appBarViewModel,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(UiState.ShowErrorLoadingData, dailyCigarettesViewModel.uiState.value)
	}

	@Test
	fun testOnComposition() {
		val observeDailySmokedCigarettesUseCase = mock<ObserveDailySmokedCigarettesUseCase>()
		val appBarViewModel = mock<AppBarViewModel>()

		whenever(observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes()).thenReturn(flow {
			emit(
				buildDailySmokedCigarettesMap()
			)
		})

		val dailyCigarettesViewModel =
			DailyCigarettesViewModelImpl(observeDailySmokedCigarettesUseCase, appBarViewModel)

		val title: MutableState<Title> = mutableStateOf(Title.EmptyTitle)
		whenever(appBarViewModel.title).thenReturn(title)

		dailyCigarettesViewModel.onComposition()

		assertEquals(DailyCigarettesTitle, title.value)
	}

	@Test
	fun testOnSmokedCigarettesPerDayClick() {
		val observeDailySmokedCigarettesUseCase = mock<ObserveDailySmokedCigarettesUseCase>()
		val appBarViewModel = mock<AppBarViewModel>()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeDailySmokedCigarettesUseCase.observeDailySmokedCigarettes()).thenReturn(flow {
			emit(
				buildDailySmokedCigarettesMap()
			)
		})

		val dailyCigarettesViewModel =
			DailyCigarettesViewModelImpl(
				observeDailySmokedCigarettesUseCase,
				appBarViewModel,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		dailyCigarettesViewModel.onSmokedCigarettesPerDayClick("2023-6-14")

		assertEquals(
			UiEvent.NavigateToSmokedCigarettesInDayDetails("2023-6-14"),
			dailyCigarettesViewModel.uiEvent.value
		)

		dailyCigarettesViewModel.eventConsumed()

		assertNull(dailyCigarettesViewModel.uiEvent.value)
	}

	private fun buildExpectedSmokedCigarettesPerDay(): List<SmokedCigarettesPerDay> {
		return listOf(
			SmokedCigarettesPerDay("2023-6-14", smokedCigaretteDtoMockList()),
			SmokedCigarettesPerDay("2023-6-15", smokedCigaretteDtoMockList(4)),
			SmokedCigarettesPerDay("2023-6-16", smokedCigaretteDtoMockList(5))
		)
	}

	private fun buildDailySmokedCigarettesMap(): Map<Calendar, List<SmokedCigaretteDto>> {
		return mapOf(
			newCalendar(14) to smokedCigaretteDtoMockList(),
			newCalendar(15) to smokedCigaretteDtoMockList(4),
			newCalendar(16) to smokedCigaretteDtoMockList(5)
		)
	}

	private fun newCalendar(day: Int): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, day, 8, 0, 0)

		return calendar
	}
}