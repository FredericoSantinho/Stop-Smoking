package neuro.stop.smoking.presentation.ui.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import neuro.stop.smoking.presentation.R

@Composable
fun BottomNavigation(navController: NavController) {
	val items = listOf(
		BottomNavItem.Home,
		BottomNavItem.DailyCigarettes,
	)
	BottomNavigation(
		backgroundColor = MaterialTheme.colors.primary
	) {
		val navBackStackEntry by navController.currentBackStackEntryAsState()
		val currentRoute = navBackStackEntry?.destination?.route
		items.forEach { item ->
			BottomNavigationItem(
				icon = {
					Icon(
						painterResource(id = item.icon),
						contentDescription = stringResource(item.stringId)
					)
				},
				label = {
					Text(
						text = stringResource(item.stringId),
						fontSize = MaterialTheme.typography.overline.fontSize,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
				},
				selectedContentColor = MaterialTheme.colors.background,
				unselectedContentColor = MaterialTheme.colors.background.copy(0.6f),
				alwaysShowLabel = true,
				selected = currentRoute == item.screenRoute,
				onClick = {
					navController.navigate(item.screenRoute) {

						navController.graph.startDestinationRoute?.let { screenRoute ->
							popUpTo(screenRoute) {
								saveState = true
							}
						}
						launchSingleTop = true
						restoreState = true
					}
				}
			)
		}
	}
}

sealed class BottomNavItem(
	@StringRes var stringId: Int,
	@DrawableRes var icon: Int,
	var screenRoute: String
) {

	object Home : BottomNavItem(R.string.home_title, R.drawable.ic_home_black_24dp, "home")
	object DailyCigarettes : BottomNavItem(
		R.string.daily_cigarettes_title,
		R.drawable.ic_cigarette_with_smoke_foreground,
		"daily_cigarettes"
	)
}