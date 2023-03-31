package neuro.stop.smoking.presentation.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import neuro.stop.smoking.presentation.di.viewModelModule
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTestRule

class MainComposableTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	private val scaffoldNode = composeTestRule.onNodeWithTag("Scaffold")

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(viewModelModule)
	}

	@Test
	fun myTest() {
		// Start the app
		composeTestRule.setContent {
			StopSmokingTheme {
				MainComposable()
			}
		}

		scaffoldNode.assertExists()
		scaffoldNode.assertIsDisplayed()
	}

}