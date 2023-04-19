package neuro.stop.smoking.presentation.ui.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import neuro.stop.smoking.domain.usecase.GetCurrentTimeMillisUseCase
import neuro.stop.smoking.domain.usecase.ObserveSmokedCigarettesUseCase
import neuro.stop.smoking.domain.usecase.ObserveStartOfCurrentDayUseCase
import neuro.stop.smoking.domain.usecase.RemoveSmokedCigaretteUseCase
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCase
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.di.presentationTestModules
import neuro.stop.smoking.presentation.mapper.SmokedCigaretteModelMapper
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModelImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTestRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog


@RunWith(RobolectricTestRunner::class)
internal class HomeComposableAcceptanceTest {
	@get:Rule
	var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

	@get:Rule
	val composeTestRule = createComposeRule()

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(presentationTestModules)
	}

	lateinit var context: Context

	private val onSmokedCigarettesText =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.SMOKED_CIGARETTES_TEXT)
	private val onSmokedCigarettesNumber =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.SMOKED_CIGARETTES_NUMBER)
	private val onLastCigaretteText =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.LAST_CIGARETTE_TEXT)
	private val onLastCigaretteMinutes =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.LAST_CIGARETTE_MINUTES)
	private val onSmokeButton =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.SMOKE_BUTTON)
	private val onSmokeButtonText =
		composeTestRule.onNodeWithTag(HomeFooterComposableTags.SMOKE_BUTTON_TEXT, true)

	@Before
	fun before() {
		ShadowLog.stream = System.out
		context = get(Context::class.java)
	}

	@Test
	fun testEmptySmokedCigarettes() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val homeViewModel = HomeViewModelImpl(
			get(ObserveSmokedCigarettesUseCase::class.java),
			get(RemoveSmokedCigaretteUseCase::class.java),
			get(SaveSmokedCigaretteUseCase::class.java),
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(GetCurrentTimeMillisUseCase::class.java),
			get(SmokedCigaretteModelMapper::class.java),
			get(AppBarViewModelImpl::class.java),
			testDispatcher
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		testDispatcher.scheduler.runCurrent()

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsNotDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertNoSmokedCigarettesExist()

		onSmokedCigarettesText.assertTextEquals(context.getString(R.string.smoked_cigarettes_today))
		onLastCigaretteText.assertTextEquals(context.getString(R.string.last_cigarette_minutes))
		onSmokeButtonText.assertTextEquals(context.getString(R.string.smoke))
	}

	@Test
	fun testSmokeButton() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val homeViewModel = HomeViewModelImpl(
			get(ObserveSmokedCigarettesUseCase::class.java),
			get(RemoveSmokedCigaretteUseCase::class.java),
			get(SaveSmokedCigaretteUseCase::class.java),
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(GetCurrentTimeMillisUseCase::class.java),
			get(SmokedCigaretteModelMapper::class.java),
			get(AppBarViewModelImpl::class.java),
			testDispatcher
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		testDispatcher.scheduler.runCurrent()

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsNotDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertNoSmokedCigarettesExist()

		onSmokedCigarettesText.assertTextEquals(context.getString(R.string.smoked_cigarettes_today))
		onLastCigaretteText.assertTextEquals(context.getString(R.string.last_cigarette_minutes))
		onSmokeButtonText.assertTextEquals(context.getString(R.string.smoke))

		onSmokeButton.performClick()
		onSmokeButton.performClick()
		onSmokeButton.performClick()

		testDispatcher.scheduler.runCurrent()

		assertEverythingIsDisplayed()
		assertThreeSmokedCigarettesAreShown()

		assertNoMoreSmokedCigarettesWhereAdded()
	}

	@Test
	fun testRemoveSmokedCigarette() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val homeViewModel = HomeViewModelImpl(
			get(ObserveSmokedCigarettesUseCase::class.java),
			get(RemoveSmokedCigaretteUseCase::class.java),
			get(SaveSmokedCigaretteUseCase::class.java),
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(GetCurrentTimeMillisUseCase::class.java),
			get(SmokedCigaretteModelMapper::class.java),
			get(AppBarViewModelImpl::class.java),
			testDispatcher
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		testDispatcher.scheduler.runCurrent()

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsNotDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertNoSmokedCigarettesExist()

		onSmokedCigarettesText.assertTextEquals(context.getString(R.string.smoked_cigarettes_today))
		onLastCigaretteText.assertTextEquals(context.getString(R.string.last_cigarette_minutes))
		onSmokeButtonText.assertTextEquals(context.getString(R.string.smoke))

		onSmokeButton.performClick()

		testDispatcher.scheduler.runCurrent()

		val onSmokedCigaretteRemoveIconButton =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + 1)

		onSmokedCigaretteRemoveIconButton.performClick()

		testDispatcher.scheduler.runCurrent()

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsNotDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertNoSmokedCigarettesDisplayed()
	}

	@Test
	fun testThreeSmokedCigarettesShown() {
		val testDispatcher: TestDispatcher = StandardTestDispatcher()

		val homeViewModel = HomeViewModelImpl(
			get(ObserveSmokedCigarettesUseCase::class.java),
			get(RemoveSmokedCigaretteUseCase::class.java),
			get(SaveSmokedCigaretteUseCase::class.java),
			get(ObserveStartOfCurrentDayUseCase::class.java),
			get(GetCurrentTimeMillisUseCase::class.java),
			get(SmokedCigaretteModelMapper::class.java),
			get(AppBarViewModelImpl::class.java),
			testDispatcher
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		testDispatcher.scheduler.runCurrent()

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsNotDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertNoSmokedCigarettesExist()

		onSmokedCigarettesText.assertTextEquals(context.getString(R.string.smoked_cigarettes_today))
		onLastCigaretteText.assertTextEquals(context.getString(R.string.last_cigarette_minutes))
		onSmokeButtonText.assertTextEquals(context.getString(R.string.smoke))

		onSmokeButton.performClick()
		onSmokeButton.performClick()
		onSmokeButton.performClick()

		testDispatcher.scheduler.runCurrent()

		for (i in 1..3) {
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_COMPOSABLE + i)
				.assertIsDisplayed()
		}

		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()

		assertThreeSmokedCigarettesAreShown()
	}

	private fun assertNoSmokedCigarettesExist() {
		val onSmokedCigaretteImage1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + 1)
		val onSmokedCigaretteAtText1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + 1)
		val onSmokedCigaretteAtTime1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 1)
		val onSmokedCigaretteRemoveIconButton1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + 1)
		val onSmokedCigaretteRemoveIcon1 =
			composeTestRule.onNodeWithTag(
				SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + 1,
				true
			)

		onSmokedCigaretteImage1.assertDoesNotExist()
		onSmokedCigaretteAtText1.assertDoesNotExist()
		onSmokedCigaretteAtTime1.assertDoesNotExist()
		onSmokedCigaretteRemoveIconButton1.assertDoesNotExist()
		onSmokedCigaretteRemoveIcon1.assertDoesNotExist()
	}

	private fun assertNoSmokedCigarettesDisplayed() {
		val onSmokedCigaretteImage1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + 1)
		val onSmokedCigaretteAtText1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + 1)
		val onSmokedCigaretteAtTime1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 1)
		val onSmokedCigaretteRemoveIconButton1 =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + 1)
		val onSmokedCigaretteRemoveIcon1 =
			composeTestRule.onNodeWithTag(
				SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + 1,
				true
			)

		onSmokedCigaretteImage1.assertIsNotDisplayed()
		onSmokedCigaretteAtText1.assertIsNotDisplayed()
		onSmokedCigaretteAtTime1.assertIsNotDisplayed()
		onSmokedCigaretteRemoveIconButton1.assertIsNotDisplayed()
		onSmokedCigaretteRemoveIcon1.assertIsNotDisplayed()
	}

	private fun assertThreeSmokedCigarettesAreShown() {
		onSmokedCigarettesNumber.assertTextEquals("3")
		onLastCigaretteMinutes.assertTextEquals("0")

		for (i in 1..3) {
			val onSmokedCigaretteComposable =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_COMPOSABLE + i)
			val onSmokedCigaretteImage =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + i)
			val onSmokedCigaretteAtText =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + i)
			val onSmokedCigaretteAtTime =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + i)
			val onSmokedCigaretteRemoveIconButton =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + i)
			val onSmokedCigaretteRemoveIcon =
				composeTestRule.onNodeWithTag(
					SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + i,
					true
				)

			onSmokedCigaretteComposable.assertIsDisplayed()
			onSmokedCigaretteImage.assertIsDisplayed()
			onSmokedCigaretteAtText.assertIsDisplayed()
			onSmokedCigaretteAtTime.assertIsDisplayed()
			onSmokedCigaretteRemoveIconButton.assertIsDisplayed()
			onSmokedCigaretteRemoveIcon.assertIsDisplayed()

			onSmokedCigaretteAtText.assertTextEquals(context.getString(R.string.smoked_a_cigarette_at))
		}
	}

	private fun assertNoMoreSmokedCigarettesWhereAdded() {
		val onSmokedCigaretteComposable =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_COMPOSABLE + 4)
		val onSmokedCigaretteImage =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + 4)
		val onSmokedCigaretteAtText =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + 4)
		val onSmokedCigaretteAtTime =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 4)
		val onSmokedCigaretteRemoveIconButton =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + 4)
		val onSmokedCigaretteRemoveIcon =
			composeTestRule.onNodeWithTag(
				SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + 4,
				true
			)

		onSmokedCigaretteComposable.assertDoesNotExist()
		onSmokedCigaretteImage.assertDoesNotExist()
		onSmokedCigaretteAtText.assertDoesNotExist()
		onSmokedCigaretteAtTime.assertDoesNotExist()
		onSmokedCigaretteRemoveIconButton.assertDoesNotExist()
		onSmokedCigaretteRemoveIcon.assertDoesNotExist()
	}

	private fun assertEverythingIsDisplayed() {
		onSmokedCigarettesText.assertIsDisplayed()
		onSmokedCigarettesNumber.assertIsDisplayed()
		onLastCigaretteText.assertIsDisplayed()
		onLastCigaretteMinutes.assertIsDisplayed()
		onSmokeButton.assertIsDisplayed()
		onSmokeButtonText.assertIsDisplayed()
	}
}
