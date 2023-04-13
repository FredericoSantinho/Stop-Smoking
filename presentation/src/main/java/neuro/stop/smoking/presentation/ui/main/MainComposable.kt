package neuro.stop.smoking.presentation.ui.main

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neuro.stop.smoking.presentation.ui.appbar.TopAppBarWithSettings
import neuro.stop.smoking.presentation.ui.main.nav.BottomNavigation
import neuro.stop.smoking.presentation.ui.main.nav.NavigationGraph
import neuro.stop.smoking.presentation.ui.settings.NavigateToSettings
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.appbar.UiEvent
import neuro.stop.smoking.presentation.viewmodel.main.DummyMainViewModel
import neuro.stop.smoking.presentation.viewmodel.main.MainViewModel
import neuro.stop.smoking.presentation.viewmodel.main.MainViewModelImpl
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
fun MainComposable(
	mainViewModel: MainViewModel = getViewModel<MainViewModelImpl>(),
	appBarViewModel: AppBarViewModel = koinInject<AppBarViewModelImpl>(),
	navigateToSettings: NavigateToSettings = koinInject()
) {
	val uiEvent by appBarViewModel.uiEvent

	val navController = rememberNavController()

	Scaffold(
		topBar = {
			TopAppBarWithSettings(
				appBarViewModel.title,
				{ appBarViewModel.onNavigateUpClick() }
			) { appBarViewModel.onSettingsButtonClick() }
		},
		bottomBar = { BottomNavigation(navController) },
		modifier = Modifier.semantics { testTag = "Scaffold" }
	) {
		NavigationGraph(navController, Modifier.padding(it))
	}

	onAppBarUiEvent(uiEvent, navigateToSettings, appBarViewModel, navController)
}

@Composable
fun onAppBarUiEvent(
	uiEvent: UiEvent?,
	navigateToSettings: NavigateToSettings,
	appBarViewModel: AppBarViewModel,
	navController: NavController
) {
	when (uiEvent) {
		is UiEvent.NavigateToSettings -> {
			navigateToSettings(navigateToSettings)
		}

		is UiEvent.NavigateUp -> navController.navigateUp()

		null -> {}
	}
	appBarViewModel.eventConsumed()
}

@Composable
fun navigateToSettings(navigateToSettings: NavigateToSettings) {
	navigateToSettings.navigateToSettings(LocalContext.current)
}

@Preview
@Composable
fun PreviewMainComposable() {
	StopSmokingTheme {
		val navigateToSettings = object : NavigateToSettings {
			override fun navigateToSettings(context: Context) {

			}
		}
		MainComposable(DummyMainViewModel(), AppBarViewModelImpl(), navigateToSettings)
	}
}
