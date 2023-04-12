package neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.presentation.MainDispatcherRule
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapper
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.DailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.viewmodel.test.mocks.smokedCigaretteModelMockList
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.TimeZone
import kotlin.test.assertEquals

class DailyCigarettesDetailsViewModelImplTest {

	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Test
	fun testHappyPath() {
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapper>()
		val savedStateHandle =
			SavedStateHandle(mapOf(DailyCigarettesDetailsActivity.DATE to "2023-5-14"))
		val testDispatcher: TestDispatcher = StandardTestDispatcher()


		val initialCalendar = newCalendar(14)
		val finalCalendar = newCalendar(15)

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar(
					14
				)
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(
				initialCalendar.timeInMillis,
				finalCalendar.timeInMillis
			)
		).thenReturn(
			flow { emit(smokedCigaretteDtoMockList()) }
		)
		whenever(smokedCigaretteModelMapper.toPresentation(smokedCigaretteDtoMockList())).thenReturn(
			smokedCigaretteModelMockList()
		)

		val dailyCigarettesDetailsViewModel = DailyCigarettesDetailsViewModelImpl(
			removeSmokedCigaretteUseCase,
			observeSmokedCigarettesUseCase,
			observeStartOfCurrentDayUseCase,
			smokedCigaretteModelMapper,
			savedStateHandle,
			testDispatcher
		)

		val expectedCigaretteUrl =
			"https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg"
		assertEquals(expectedCigaretteUrl, dailyCigarettesDetailsViewModel.cigaretteUrl.value)

		testDispatcher.scheduler.runCurrent()

		val expectedSmokedCigaretteModelList = smokedCigaretteModelMockList()
		assertEquals(
			expectedSmokedCigaretteModelList,
			dailyCigarettesDetailsViewModel.smokedCigarettes.value
		)
	}

	@Test
	fun testErrorObservingStartOfCurrentDay() {
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapper>()
		val savedStateHandle =
			SavedStateHandle(mapOf(DailyCigarettesDetailsActivity.DATE to "2023-5-14"))
		val testDispatcher: TestDispatcher = StandardTestDispatcher()


		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			error("")
		})

		val dailyCigarettesDetailsViewModel = DailyCigarettesDetailsViewModelImpl(
			removeSmokedCigaretteUseCase,
			observeSmokedCigarettesUseCase,
			observeStartOfCurrentDayUseCase,
			smokedCigaretteModelMapper,
			savedStateHandle,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			UiState.ShowErrorLoadingSmokedCigarettes,
			dailyCigarettesDetailsViewModel.uiState.value
		)
	}

	@Test
	fun testOnRemoveCigaretteClick() = runBlocking {
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapper>()
		val savedStateHandle =
			SavedStateHandle(mapOf(DailyCigarettesDetailsActivity.DATE to "2023-5-14"))
		val testDispatcher: TestDispatcher = StandardTestDispatcher()


		val initialCalendar = newCalendar(14)
		val finalCalendar = newCalendar(15)

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar(
					14
				)
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(
				initialCalendar.timeInMillis,
				finalCalendar.timeInMillis
			)
		).thenReturn(
			flow { emit(smokedCigaretteDtoMockList()) }
		)
		whenever(smokedCigaretteModelMapper.toPresentation(smokedCigaretteDtoMockList())).thenReturn(
			smokedCigaretteModelMockList()
		)

		val dailyCigarettesDetailsViewModel = DailyCigarettesDetailsViewModelImpl(
			removeSmokedCigaretteUseCase,
			observeSmokedCigarettesUseCase,
			observeStartOfCurrentDayUseCase,
			smokedCigaretteModelMapper,
			savedStateHandle,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		dailyCigarettesDetailsViewModel.onRemoveCigaretteClick(1)

		verifyNoInteractions(removeSmokedCigaretteUseCase)

		testDispatcher.scheduler.runCurrent()

		verify(removeSmokedCigaretteUseCase, times(1)).removeSmokedCigarette(1)
	}

	@Test
	fun testErrorRemovingCigarette() = runBlocking {
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapper>()
		val savedStateHandle =
			SavedStateHandle(mapOf(DailyCigarettesDetailsActivity.DATE to "2023-5-14"))
		val testDispatcher: TestDispatcher = StandardTestDispatcher()


		val initialCalendar = newCalendar(14)
		val finalCalendar = newCalendar(15)

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar(
					14
				)
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(
				initialCalendar.timeInMillis,
				finalCalendar.timeInMillis
			)
		).thenReturn(
			flow { emit(smokedCigaretteDtoMockList()) }
		)
		whenever(smokedCigaretteModelMapper.toPresentation(smokedCigaretteDtoMockList())).thenReturn(
			smokedCigaretteModelMockList()
		)
		whenever(removeSmokedCigaretteUseCase.removeSmokedCigarette(1)).thenThrow(
			IllegalArgumentException()
		)

		val dailyCigarettesDetailsViewModel = DailyCigarettesDetailsViewModelImpl(
			removeSmokedCigaretteUseCase,
			observeSmokedCigarettesUseCase,
			observeStartOfCurrentDayUseCase,
			smokedCigaretteModelMapper,
			savedStateHandle,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		dailyCigarettesDetailsViewModel.onRemoveCigaretteClick(1)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			UiState.ShowErrorRemovingSmokedCigarette,
			dailyCigarettesDetailsViewModel.uiState.value
		)
	}

	private fun newCalendar(day: Int): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.timeInMillis = 0

		calendar.set(2023, 4, day, 8, 0, 0)

		return calendar
	}
}