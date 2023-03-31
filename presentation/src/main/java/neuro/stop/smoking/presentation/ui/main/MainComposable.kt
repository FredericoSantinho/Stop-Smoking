package neuro.stop.smoking.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import neuro.stop.smoking.presentation.ui.appbar.TopAppBar
import neuro.stop.smoking.presentation.ui.main.nav.BottomNavigation
import neuro.stop.smoking.presentation.ui.main.nav.NavigationGraph
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import org.koin.compose.koinInject

@Composable
fun MainComposable(appBarViewModel: AppBarViewModel = koinInject()) {
	val navController = rememberNavController()

	Scaffold(
		topBar = { TopAppBar(navController, appBarViewModel) },
		bottomBar = { BottomNavigation(navController) },
		modifier = Modifier.semantics { testTag = "Scaffold" }
	) {
		NavigationGraph(navController, Modifier.padding(it))
	}
}

@Preview
@Composable
fun PreviewMainComposable() {
	StopSmokingTheme {
		MainComposable(AppBarViewModel())
	}
}
