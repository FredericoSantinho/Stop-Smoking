package neuro.stop.smoking.presentation.ui.main

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import neuro.stop.smoking.data.di.daoModule
import neuro.stop.smoking.data.di.repositoryModule
import neuro.stop.smoking.data.di.test.memoryDatabaseModule
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.di.contextModule
import neuro.stop.smoking.presentation.di.presentationMappersModule
import neuro.stop.smoking.presentation.di.presentationModule
import neuro.stop.smoking.presentation.di.useCaseModule
import neuro.stop.smoking.presentation.di.viewModelModule
import neuro.stop.smoking.presentation.ui.appbar.TopAppBarWithSettingsTags
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.home.HomeTitle
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTestRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class MainComposableTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	private lateinit var context: Context

	private val scaffoldNode = composeTestRule.onNodeWithTag("Scaffold")
	private val onTopAppBarTitle =
		composeTestRule.onNodeWithTag(TopAppBarWithSettingsTags.TOP_APP_BAR_TITLE, true)

	@get:Rule
	val koinTestRule = KoinTestRule.create {
		modules(
			viewModelModule,
			presentationModule,
			useCaseModule,
			repositoryModule,
			daoModule,
			memoryDatabaseModule,
			contextModule,
			presentationMappersModule
		)
	}

	@Before
	fun before() {
		ShadowLog.stream = System.out
		context = KoinJavaComponent.get(Context::class.java)
	}


	@Test
	fun testTitle() {
		val appBarViewModel = AppBarViewModelImpl()
		appBarViewModel.title.value = HomeTitle

		composeTestRule.setContent {
			StopSmokingTheme {
				MainComposable(appBarViewModel = appBarViewModel)
			}
		}

		scaffoldNode.assertExists()
		scaffoldNode.assertIsDisplayed()

		onTopAppBarTitle.assertIsDisplayed()
		onTopAppBarTitle.assertTextEquals(context.getString(R.string.home_title))
	}
}