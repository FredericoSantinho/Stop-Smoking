package neuro.stop.smoking.presentation.ui.settings.items

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.SetStartOfDayUseCase
import neuro.stop.smoking.presentation.R
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
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class ChangeStartOfDayComposableAcceptanceTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(presentationTestModules)
	}

	lateinit var context: Context

	private val onChangeStartOfDay =
		composeTestRule.onNodeWithTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY)
	private val onChangeStartOfDayCopy =
		composeTestRule.onNodeWithTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY_COPY)
	private val onChangeStartOfDayTime =
		composeTestRule.onNodeWithTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY_TIME)

	@Before
	@Throws(Exception::class)
	fun setUp() {
		ShadowLog.stream = System.out
		context = ApplicationProvider.getApplicationContext()
	}

	@Test
	fun testHappyPath() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val changeStartOfDayViewModel = ChangeStartOfDayViewModelImpl(
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(SetStartOfDayUseCase::class.java),
			get(TimeTextMapper::class.java),
			testDispatcher
		)
		val showTimePicker: ShowTimePicker = get(ShowTimePicker::class.java)

		composeTestRule.setContent {
			StopSmokingTheme {
				ChangeStartOfDayComposable(changeStartOfDayViewModel, showTimePicker)
			}
		}

		testDispatcher.scheduler.runCurrent()

		onChangeStartOfDay.assertIsDisplayed()
		onChangeStartOfDayCopy.assertIsDisplayed()
		onChangeStartOfDayTime.assertIsDisplayed()

		onChangeStartOfDayCopy.assertTextEquals(context.getString(R.string.change_start_of_day))
		onChangeStartOfDayTime.assertTextEquals("00h00")
	}
}
