package neuro.stop.smoking.presentation.ui.daily.cigarettes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.runBlocking
import neuro.stop.smoking.presentation.ui.daily.cigarettes.DailyCigarettesRowComposableTags.Companion.DAILY_CIGARETTES_ROW_CARD
import neuro.stop.smoking.presentation.ui.daily.cigarettes.DailyCigarettesRowComposableTags.Companion.DAILY_CIGARETTES_ROW_DATE
import neuro.stop.smoking.presentation.ui.daily.cigarettes.DailyCigarettesRowComposableTags.Companion.DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesViewModelMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
class DailyCigarettesComposableUiTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@Before
	fun before() {
		ShadowLog.stream = System.out
	}

	@Test
	fun test() {
		val onDailyCigarettesRowCard1 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_CARD + "2023-5-19")
		val onDailyCigarettesRowDate1 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_DATE + "2023-5-19", true)
		val onDailyCigarettesRowSmokedCigarettesNumber1 =
			composeTestRule.onNodeWithTag(
				DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER + "2023-5-19",
				true
			)

		val onDailyCigarettesRowCard2 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_CARD + "2023-5-18")
		val onDailyCigarettesRowDate2 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_DATE + "2023-5-18", true)
		val onDailyCigarettesRowSmokedCigarettesNumber2 =
			composeTestRule.onNodeWithTag(
				DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER + "2023-5-18",
				true
			)

		val onDailyCigarettesRowCard3 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_CARD + "2023-5-17")
		val onDailyCigarettesRowDate3 =
			composeTestRule.onNodeWithTag(DAILY_CIGARETTES_ROW_DATE + "2023-5-17", true)
		val onDailyCigarettesRowSmokedCigarettesNumber3 =
			composeTestRule.onNodeWithTag(
				DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER + "2023-5-17",
				true
			)

		val dailyCigarettesViewModel = DailyCigarettesViewModelMock()
		val navigateToDailyCigarettesDetailsActivity = mock<NavigateToDailyCigarettesDetailsActivity>()
		composeTestRule.setContent {
			StopSmokingTheme {
				DailyCigarettesComposable(
					dailyCigarettesViewModel,
					navigateToDailyCigarettesDetailsActivity
				)
			}
		}

		onDailyCigarettesRowCard1.assertIsDisplayed()
		onDailyCigarettesRowDate1.assertIsDisplayed()
		onDailyCigarettesRowSmokedCigarettesNumber1.assertIsDisplayed()

		onDailyCigarettesRowDate1.assertTextEquals("2023-5-19")
		onDailyCigarettesRowSmokedCigarettesNumber1.assertTextEquals("3")

		onDailyCigarettesRowCard2.assertIsDisplayed()
		onDailyCigarettesRowDate2.assertIsDisplayed()
		onDailyCigarettesRowSmokedCigarettesNumber2.assertIsDisplayed()

		onDailyCigarettesRowDate2.assertTextEquals("2023-5-18")
		onDailyCigarettesRowSmokedCigarettesNumber2.assertTextEquals("5")

		onDailyCigarettesRowCard3.assertIsDisplayed()
		onDailyCigarettesRowDate3.assertIsDisplayed()
		onDailyCigarettesRowSmokedCigarettesNumber3.assertIsDisplayed()

		onDailyCigarettesRowDate3.assertTextEquals("2023-5-17")
		onDailyCigarettesRowSmokedCigarettesNumber3.assertTextEquals("7")

		assertNull(dailyCigarettesViewModel.uiEvent.value)

		onDailyCigarettesRowCard1.performClick()

		runBlocking {
			composeTestRule.awaitIdle()
		}

		verify(
			navigateToDailyCigarettesDetailsActivity,
			times(1)
		).navigateToDailyCigarettesDetailsActivity(
			any(), eq("2023-5-19")
		)
	}
}