package neuro.stop.smoking.presentation.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.di.presentationTestModules
import neuro.stop.smoking.presentation.ui.common.dialog.ErrorDialogTags
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.home.HomeUiState
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
internal class HomeComposableUiTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(presentationTestModules)
	}

	lateinit var context: Context

	private val onErrorDialog = composeTestRule.onNodeWithTag(ErrorDialogTags.ERROR_DIALOG)
	private val onErrorDialogTitle = composeTestRule.onNodeWithTag(ErrorDialogTags.ERROR_DIALOG_TITLE)
	private val onErrorDialogDescription =
		composeTestRule.onNodeWithTag(ErrorDialogTags.ERROR_DIALOG_DESCRIPTION)
	private val onErrorDialogButtonText =
		composeTestRule.onNodeWithTag(ErrorDialogTags.ERROR_DIALOG_BUTTON_TEXT, true)

	@Before
	fun before() {
		ShadowLog.stream = System.out
		context = get(Context::class.java)
	}

	@Test
	fun testErrorLoadingData() {
		val homeViewModel = mock<HomeViewModel>()

		whenever(homeViewModel.smokedCigarettes).thenReturn(mutableStateOf(emptyList()))
		whenever(homeViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.smokedCigarettesNumber).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.lastCigaretteTimeMinutes).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.uiState).thenReturn(
			mutableStateOf(
				HomeUiState.UiState.ShowError(listOf(HomeUiState.ErrorDescription.ErrorLoadingData))
			)
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		assertErrorIsDisplayedCorrectly(R.string.error_loading_data)
	}

	@Test
	fun testErrorRemovingSmokedCigarette() {
		val homeViewModel = mock<HomeViewModel>()

		whenever(homeViewModel.smokedCigarettes).thenReturn(mutableStateOf(emptyList()))
		whenever(homeViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.smokedCigarettesNumber).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.lastCigaretteTimeMinutes).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.uiState).thenReturn(
			mutableStateOf(
				HomeUiState.UiState.ShowError(listOf(HomeUiState.ErrorDescription.ErrorRemovingSmokedCigarette))
			)
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		assertErrorIsDisplayedCorrectly(R.string.error_removing_smoked_cigarette)
	}

	@Test
	fun testErrorSavingSmokedCigarette() {
		val homeViewModel = mock<HomeViewModel>()

		whenever(homeViewModel.smokedCigarettes).thenReturn(mutableStateOf(emptyList()))
		whenever(homeViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.smokedCigarettesNumber).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.lastCigaretteTimeMinutes).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.uiState).thenReturn(
			mutableStateOf(
				HomeUiState.UiState.ShowError(listOf(HomeUiState.ErrorDescription.ErrorSavingSmokedCigarette))
			)
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		assertErrorIsDisplayedCorrectly(R.string.error_saving_smoked_cigarette)
	}

	@Test
	fun testErrorUpdatingLastCigaretteTime() {
		val homeViewModel = mock<HomeViewModel>()

		whenever(homeViewModel.smokedCigarettes).thenReturn(mutableStateOf(emptyList()))
		whenever(homeViewModel.cigaretteUrl).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.smokedCigarettesNumber).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.lastCigaretteTimeMinutes).thenReturn(mutableStateOf(""))
		whenever(homeViewModel.uiState).thenReturn(
			mutableStateOf(
				HomeUiState.UiState.ShowError(listOf(HomeUiState.ErrorDescription.ErrorUpdatingLastCigaretteTime))
			)
		)

		composeTestRule.setContent {
			StopSmokingTheme {
				HomeComposable(homeViewModel)
			}
		}

		assertErrorIsDisplayedCorrectly(R.string.error_updating_last_cigarette_time)
	}

	private fun assertErrorIsDisplayedCorrectly(descriptionId: Int) {
		onErrorDialog.assertIsDisplayed()
		onErrorDialogTitle.assertIsDisplayed()
		onErrorDialogDescription.assertIsDisplayed()
		onErrorDialogButtonText.assertIsDisplayed()

		onErrorDialogTitle.assertTextEquals(context.getString(R.string.error_title))
		onErrorDialogDescription.assertTextEquals(context.getString(descriptionId))
		onErrorDialogButtonText.assertTextEquals(context.getString(R.string.ok))
	}

}
