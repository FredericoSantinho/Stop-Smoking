package neuro.stop.smoking.presentation.ui.daily.cigarettes.details

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.di.presentationTestModules
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.ui.appbar.SimpleTopAppBarTags
import neuro.stop.smoking.presentation.ui.home.SmokedCigaretteComposableTags
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsTitle
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsViewModel
import neuro.stop.smoking.presentation.viewmodel.test.mocks.smokedCigaretteModelMock
import neuro.stop.smoking.presentation.viewmodel.test.mocks.smokedCigaretteModelMockList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTestRule
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class DailyCigarettesDetailsComposableUiTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(presentationTestModules)
	}

	lateinit var context: Context

	val onSimpleTopAppBar = composeTestRule.onNodeWithTag(SimpleTopAppBarTags.TOP_APP_BAR)
	val onSimpleTopAppBarTitle = composeTestRule.onNodeWithTag(SimpleTopAppBarTags.TOP_APP_BAR_TITLE)

	@Before
	fun before() {
		ShadowLog.stream = System.out
		context = get(Context::class.java)
	}

	@Test
	fun testUi() {
		val dailyCigarettesDetailsViewModel = mock<DailyCigarettesDetailsViewModel>()

		whenever(dailyCigarettesDetailsViewModel.date).thenReturn(mutableStateOf("2023-5-16"))
		whenever(dailyCigarettesDetailsViewModel.title).thenReturn(
			mutableStateOf(
				DailyCigarettesDetailsTitle("2023-5-16")
			)
		)
		whenever(dailyCigarettesDetailsViewModel.smokedCigarettes).thenReturn(
			mutableStateOf(
				buildSmokedCigaretteModelList()
			)
		)
		whenever(dailyCigarettesDetailsViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))

		composeTestRule.setContent {
			StopSmokingTheme {
				DailyCigarettesDetailsComposable(dailyCigarettesDetailsViewModel)
			}
		}

		onSimpleTopAppBar.assertIsDisplayed()
		onSimpleTopAppBarTitle.assertIsDisplayed()

		onSimpleTopAppBarTitle.assertTextEquals(
			context.getString(
				R.string.daily_cigarettes_details_title,
				"2023-5-16"
			)
		)

		for (i in 1..5) {
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_COMPOSABLE + i)
				.assertIsDisplayed()
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + i)
				.assertIsDisplayed()
			val onSmokedCigaretteAtText =
				composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + i)
			onSmokedCigaretteAtText.assertIsDisplayed()
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + i)
				.assertIsDisplayed()
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + i)
				.assertIsDisplayed()
			composeTestRule.onNodeWithTag(
				SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + i,
				true
			).assertIsDisplayed()

			onSmokedCigaretteAtText.assertTextEquals(context.getString(R.string.smoked_a_cigarette_at))
		}

		composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 1)
			.assertTextEquals("14h43")
		composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 2)
			.assertTextEquals("14h00")
		composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 3)
			.assertTextEquals("13h12")
		composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 4)
			.assertTextEquals("12h34")
		composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + 5)
			.assertTextEquals("12h13")
	}

	@Test
	fun testRemoveSmokedCigarette() {
		val dailyCigarettesDetailsViewModel = mock<DailyCigarettesDetailsViewModel>()

		whenever(dailyCigarettesDetailsViewModel.title).thenReturn(
			mutableStateOf(
				DailyCigarettesDetailsTitle("")
			)
		)
		whenever(dailyCigarettesDetailsViewModel.smokedCigarettes).thenReturn(
			mutableStateOf(
				smokedCigaretteModelMockList()
			)
		)
		whenever(dailyCigarettesDetailsViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))

		composeTestRule.setContent {
			StopSmokingTheme {
				DailyCigarettesDetailsComposable(dailyCigarettesDetailsViewModel)
			}
		}

		val onSmokedCigaretteRemoveIconButton =
			composeTestRule.onNodeWithTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + 1)

		verify(dailyCigarettesDetailsViewModel, times(0)).onRemoveCigaretteClick(anyLong())

		onSmokedCigaretteRemoveIconButton.performClick()

		verify(dailyCigarettesDetailsViewModel, times(1)).onRemoveCigaretteClick(1L)
	}

	private fun buildSmokedCigaretteModelList(): List<SmokedCigaretteModel> {
		return listOf(
			smokedCigaretteModelMock(1, "14h43"),
			smokedCigaretteModelMock(2, "14h00"),
			smokedCigaretteModelMock(3, "13h12"),
			smokedCigaretteModelMock(4, "12h34"),
			smokedCigaretteModelMock(5, "12h13")
		)
	}
}