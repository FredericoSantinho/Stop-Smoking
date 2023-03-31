package neuro.stop.smoking.presentation.ui.main.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import neuro.stop.smoking.presentation.ui.daily.cigarettes.DailyCigarettesComposable
import neuro.stop.smoking.presentation.ui.home.HomeComposable

@Composable
fun NavigationGraph(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(navController, startDestination = BottomNavItem.Home.screenRoute, modifier = modifier) {
		composable(BottomNavItem.Home.screenRoute) {
			HomeComposable()
		}
		composable(BottomNavItem.DailyCigarettes.screenRoute) {
			DailyCigarettesComposable()
		}
	}
}
