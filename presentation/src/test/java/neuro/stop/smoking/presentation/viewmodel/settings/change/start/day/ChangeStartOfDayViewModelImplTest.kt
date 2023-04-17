package neuro.stop.smoking.presentation.viewmodel.settings.change.start.day

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCase
import neuro.stop.smoking.presentation.MainDispatcherRule
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapper
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapperImpl
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ChangeStartOfDayViewModelImplTest {

	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Test
	fun testHappyPath() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newCalendar()) }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(8, changeStartOfDayViewModel.hour.value)
		assertEquals(0, changeStartOfDayViewModel.minute.value)
	}

	@Test
	fun testErrorObservingStartOfCurrentDay() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { error("") }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(0, changeStartOfDayViewModel.hour.value)
		assertEquals(0, changeStartOfDayViewModel.minute.value)
		assertEquals(
			UiState.Error(UiState.ErrorDescription.ErrorGettingStartOfDay),
			changeStartOfDayViewModel.uiState.value
		)
	}

	@Test
	fun testTitle() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newCalendar()) }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		assertEquals(ChangeStartOfDayTitle, changeStartOfDayViewModel.title.value)
	}

	@Test
	fun testOnTimeClickAndOnTimeChange() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newCalendar()) }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		assertEquals(UiState.Ready, changeStartOfDayViewModel.uiState.value)

		changeStartOfDayViewModel.onTimeClick()

		assertEquals(UiState.ShowTimePicker, changeStartOfDayViewModel.uiState.value)

		changeStartOfDayViewModel.onTimeChange(10, 0)

		testDispatcher.scheduler.runCurrent()

		assertEquals(UiState.Ready, changeStartOfDayViewModel.uiState.value)
	}

	@Test
	fun testOnTimeChangeError() = runBlocking {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newCalendar()) }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		whenever(setStartOfDayUseCase.setStartOfDay(any())).thenThrow(IllegalStateException())

		assertEquals(UiState.Ready, changeStartOfDayViewModel.uiState.value)

		changeStartOfDayViewModel.onTimeChange(10, 0)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			UiState.Error(UiState.ErrorDescription.ErrorSettingStartOfDay),
			changeStartOfDayViewModel.uiState.value
		)
	}

	@Test
	fun testOnErrorDismiss() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { error("") }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			UiState.Error(UiState.ErrorDescription.ErrorGettingStartOfDay),
			changeStartOfDayViewModel.uiState.value
		)

		changeStartOfDayViewModel.onErrorDismiss()

		assertEquals(UiState.Ready, changeStartOfDayViewModel.uiState.value)
	}

	@Test
	fun testOnErrorOkButtonClick() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { error("") }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertEquals(
			UiState.Error(UiState.ErrorDescription.ErrorGettingStartOfDay),
			changeStartOfDayViewModel.uiState.value
		)

		changeStartOfDayViewModel.onErrorOkButtonClick()

		assertEquals(UiState.Ready, changeStartOfDayViewModel.uiState.value)
	}

	@Test
	fun testOnBackButtonClick() {
		val observeStartOfCurrentDayUseCase = mock<ObserveStartOfCurrentDayUseCase>()
		val setStartOfDayUseCase = mock<SetStartOfDayUseCase>()
		val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		whenever(observeStartOfCurrentDayUseCase.observeStartOfCurrentDay(anyOrNull())).thenReturn(
			flow { emit(newCalendar()) }
		)

		val changeStartOfDayViewModel =
			ChangeStartOfDayViewModelImpl(
				observeStartOfCurrentDayUseCase,
				setStartOfDayUseCase,
				timeTextMapper,
				testDispatcher
			)

		testDispatcher.scheduler.runCurrent()

		assertNull(changeStartOfDayViewModel.uiEvent.value)

		changeStartOfDayViewModel.onBackButtonClick()

		assertEquals(UiEvent.NavigateBack, changeStartOfDayViewModel.uiEvent.value)

		changeStartOfDayViewModel.eventConsumed()

		assertNull(changeStartOfDayViewModel.uiEvent.value)
	}

	private fun newCalendar(): Calendar {
		val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
		val calendar = Calendar.getInstance(timeZone)
		calendar.time = Date(0)

		calendar.set(2023, 5, 14, 8, 0, 0)

		return calendar
	}

}