package neuro.stop.smoking.presentation.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import org.junit.Rule
import org.junit.Test

class MainComposableTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	private val helloAndroidNode = composeTestRule.onNodeWithTag("Android")

	@Test
	fun myTest() {
		// Start the app
		composeTestRule.setContent {
			StopSmokingTheme {
				MainComposable()
			}
		}

		helloAndroidNode.assertExists()
		helloAndroidNode.assertIsDisplayed()
		helloAndroidNode.assertTextEquals("Hello Android!")
	}

}