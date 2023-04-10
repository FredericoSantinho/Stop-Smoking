package neuro.stop.smoking.presentation.viewmodel.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMock
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import neuro.stop.smoking.domain.usecase.GetCurrentTimeMillisUseCaseImpl
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCase
import neuro.stop.smoking.presentation.MainDispatcherRule
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapperImpl
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
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
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class HomeViewModelImplTest {

	private val smokedCigaretteDto2 = smokedCigaretteDtoMock(1, 60 * 1000)
	private val smokedCigaretteDto1 = smokedCigaretteDtoMock(3, 2 * 60 * 1000)
	private val smokedCigaretteDto0 = smokedCigaretteDtoMock(5, 1686729600000 + 3 * 60 * 1000)

	private val smokedCigaretteModel0 = SmokedCigaretteModel(5, "00h03")
	private val smokedCigaretteModel1 = SmokedCigaretteModel(3, "00h02")
	private val smokedCigaretteModel2 = SmokedCigaretteModel(1, "00h01")

	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Test
	fun testHappyPath() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(buildSmokedCigarettesList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(1686729600000L + 5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(buildSmokedCigarettesList())).thenReturn(
			buildSmokedCigarettesModelsList()
		)

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		val expectedCigaretteUrl =
			"https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg"
		assertEquals(expectedCigaretteUrl, homeViewModel.cigaretteUrl.value)

		testDispatcher.scheduler.runCurrent()

		assertEquals("3", homeViewModel.smokedCigarettesNumber.value)
		assertEquals("2", homeViewModel.lastCigaretteTimeMinutes.value)
		assertEquals(buildSmokedCigarettesModelsList(), homeViewModel.smokedCigarettes.value)
	}

	@Test
	fun testHappyPathEmptySmokedCigarettes() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(emptyList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(1686729600000L + 5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(emptyList())).thenReturn(emptyList())

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		assertEquals("0", homeViewModel.smokedCigarettesNumber.value)
		assertEquals("", homeViewModel.lastCigaretteTimeMinutes.value)
		assertEquals(emptyList(), homeViewModel.smokedCigarettes.value)
	}

	@Test
	fun testErrorLoadingData() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			error("")
		})

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		assertEquals("", homeViewModel.smokedCigarettesNumber.value)
		assertEquals("", homeViewModel.lastCigaretteTimeMinutes.value)
		assertEquals(emptyList(), homeViewModel.smokedCigarettes.value)

		val expectedError =
			HomeUiState.UiState.ShowError(listOf(HomeUiState.ErrorDescription.ErrorLoadingData))

		assertEquals(expectedError, homeViewModel.uiState.value)
	}

	@Test
	fun testOnComposition() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(emptyList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(emptyList())).thenReturn(emptyList())

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		val title: MutableState<Title> = mutableStateOf(Title.EmptyTitle)
		whenever(appBarViewModel.title).thenReturn(title)

		homeViewModel.onComposition()

		assertEquals(HomeTitle, title.value)
	}

	@Test
	fun testOnRemoveCigaretteClick() = runBlocking {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(buildSmokedCigarettesList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(buildSmokedCigarettesList())).thenReturn(
			buildSmokedCigarettesModelsList()
		)

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		verifyNoInteractions(removeSmokedCigaretteUseCase)

		homeViewModel.onRemoveCigaretteClick(1)

		testDispatcher.scheduler.runCurrent()

		verify(removeSmokedCigaretteUseCase, times(1)).removeSmokedCigarette(1)
	}

	@Test
	fun testOnSmokeButtonClick() = runBlocking {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(emptyList()) })
		val currentTimeMillis: Long = 5 * 60 * 1000
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(currentTimeMillis)
		whenever(smokedCigaretteModelMapper.toPresentation(emptyList())).thenReturn(emptyList())

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		verifyNoInteractions(saveSmokedCigaretteUseCase)

		homeViewModel.onSmokeButtonClick()

		testDispatcher.scheduler.runCurrent()

		verify(saveSmokedCigaretteUseCase, times(1)).saveSmokedCigaretteUseCase(currentTimeMillis)
	}

	@Test
	fun testOnErrorDismiss() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			error("")
		})

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		assertNotEquals(HomeUiState.UiState.Ready, homeViewModel.uiState.value)

		homeViewModel.onErrorDismiss()

		assertEquals(HomeUiState.UiState.Ready, homeViewModel.uiState.value)
	}

	@Test
	fun testOnErrorOkButtonClick() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			error("")
		})

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		assertNotEquals(HomeUiState.UiState.Ready, homeViewModel.uiState.value)

		homeViewModel.onErrorOkButtonClick()

		assertEquals(HomeUiState.UiState.Ready, homeViewModel.uiState.value)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@Test
	fun testLastCigaretteTimeIncrementer() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(buildSmokedCigarettesList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(1686729600000L + 5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(buildSmokedCigarettesList())).thenReturn(
			buildSmokedCigarettesModelsList()
		)

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		val expectedCigaretteUrl =
			"https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg"
		assertEquals(expectedCigaretteUrl, homeViewModel.cigaretteUrl.value)

		testDispatcher.scheduler.runCurrent()

		assertEquals("2", homeViewModel.lastCigaretteTimeMinutes.value)

		testDispatcher.scheduler.advanceTimeBy(59 * 1000)
		testDispatcher.scheduler.runCurrent()

		assertEquals("2", homeViewModel.lastCigaretteTimeMinutes.value)

		testDispatcher.scheduler.advanceTimeBy(1000)
		testDispatcher.scheduler.runCurrent()

		assertEquals("3", homeViewModel.lastCigaretteTimeMinutes.value)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@Test
	fun testPostponeDayChange() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val observeSmokedCigarettesUseCase = mock<ObserveSmokedCigarettesUseCase>()
		val removeSmokedCigaretteUseCase = mock<RemoveSmokedCigaretteUseCase>()
		val saveSmokedCigaretteUseCase = mock<SaveSmokedCigaretteUseCase>()
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val getCurrentTimeMillisUseCase = mock<GetCurrentTimeMillisUseCaseImpl>()
		val smokedCigaretteModelMapper = mock<SmokedCigaretteModelMapperImpl>()
		val appBarViewModel = mock<AppBarViewModelImpl>()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(flow {
			emit(
				newCalendar()
			)
		})
		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(buildSmokedCigarettesList()) })
		whenever(getCurrentTimeMillisUseCase.getCurrentTimeMillis()).thenReturn(1686729600000L + 5 * 60 * 1000)
		whenever(smokedCigaretteModelMapper.toPresentation(buildSmokedCigarettesList())).thenReturn(
			buildSmokedCigarettesModelsList()
		)

		val homeViewModel = HomeViewModelImpl(
			observeSmokedCigarettesUseCase,
			removeSmokedCigaretteUseCase,
			saveSmokedCigaretteUseCase,
			observeStartOfCurrentDayUseCase,
			getCurrentTimeMillisUseCase,
			smokedCigaretteModelMapper,
			appBarViewModel,
			testDispatcher
		)

		testDispatcher.scheduler.runCurrent()

		testDispatcher.scheduler.runCurrent()

		assertEquals("3", homeViewModel.smokedCigarettesNumber.value)
		assertEquals("2", homeViewModel.lastCigaretteTimeMinutes.value)
		assertEquals(buildSmokedCigarettesModelsList(), homeViewModel.smokedCigarettes.value)
		verify(observeStartOfCurrentDayUseCase, times(2)).observeStartOfCurrentDay(anyOrNull())

		testDispatcher.scheduler.advanceTimeBy(86100000L)

		verify(observeStartOfCurrentDayUseCase, times(2)).observeStartOfCurrentDay(anyOrNull())

		whenever(
			observeSmokedCigarettesUseCase.observeSmokedCigarettes(1686729600000L, Long.MAX_VALUE)
		).thenReturn(flow { emit(smokedCigaretteDtoMockList()) })
		whenever(smokedCigaretteModelMapper.toPresentation(smokedCigaretteDtoMockList())).thenReturn(
			smokedCigaretteModelMockList()
		)

		testDispatcher.scheduler.advanceTimeBy(1)

		verify(observeStartOfCurrentDayUseCase, times(4)).observeStartOfCurrentDay(anyOrNull())
		assertEquals("3", homeViewModel.smokedCigarettesNumber.value)
		assertEquals("28112164", homeViewModel.lastCigaretteTimeMinutes.value)
		assertEquals(smokedCigaretteModelMockList(), homeViewModel.smokedCigarettes.value)
	}

	private fun buildSmokedCigarettesList(): List<SmokedCigaretteDto> {
		return listOf(
			smokedCigaretteDto0,
			smokedCigaretteDto1,
			smokedCigaretteDto2
		)
	}

	private fun buildSmokedCigarettesModelsList(): List<SmokedCigaretteModel> {
		return listOf(
			smokedCigaretteModel0,
			smokedCigaretteModel1,
			smokedCigaretteModel2
		)
	}

	private fun newCalendar(): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, 8, 0, 0)

		return calendar
	}
}

