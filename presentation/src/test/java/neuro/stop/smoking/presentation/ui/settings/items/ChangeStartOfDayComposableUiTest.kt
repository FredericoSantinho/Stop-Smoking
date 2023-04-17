package neuro.stop.smoking.presentation.ui.settings.items

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCase
import neuro.stop.smoking.presentation.di.presentationTestModules
import neuro.stop.smoking.presentation.ui.common.datetime.ShowTimePicker
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapper
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.ChangeStartOfDayViewModelImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTestRule
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class ChangeStartOfDayComposableUiTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(presentationTestModules)
	}

	private val onChangeStartOfDayTime =
		composeTestRule.onNodeWithTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY_TIME)

	@Before
	@Throws(Exception::class)
	fun setUp() {
		ShadowLog.stream = System.out
	}


	@Test
	fun testOnTimeClick() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val changeStartOfDayViewModel = ChangeStartOfDayViewModelImpl(
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(SetStartOfDayUseCase::class.java),
			get(TimeTextMapper::class.java),
			testDispatcher
		)

		val showTimePicker = mock<ShowTimePicker>()

		composeTestRule.setContent {
			StopSmokingTheme {
				ChangeStartOfDayComposable(changeStartOfDayViewModel, showTimePicker)
			}
		}

		testDispatcher.scheduler.runCurrent()

		verifyNoInteractions(showTimePicker)

		onChangeStartOfDayTime.performClick()

		runBlocking {
			composeTestRule.awaitIdle()
		}

		verify(showTimePicker, times(1)).showTimePicker(
			any(),
			eq(0),
			eq(0),
			any()
		)
	}
}
