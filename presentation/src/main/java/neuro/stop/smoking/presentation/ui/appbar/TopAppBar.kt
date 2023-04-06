package neuro.stop.smoking.presentation.ui.appbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel

@Composable
fun TopAppBar(navController: NavController, appBarViewModel: AppBarViewModel) {
	androidx.compose.material.TopAppBar(
		elevation = 4.dp,
		title = { },
		backgroundColor = MaterialTheme.colors.primary,
		navigationIcon = {
			IconButton(onClick = { navController.navigateUp() }) {
				Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
			}
		},
		actions = {
			ConstraintLayout(Modifier.fillMaxSize()) {
				val (titleC, configIconC) = createRefs()

				Text(
					appBarViewModel.title.value.toPresentation(),
					Modifier.constrainAs(titleC) {
						linkTo(top = parent.top, bottom = parent.bottom)
					},
					color = Color.White,
					style = MaterialTheme.typography.h6,
					fontWeight = FontWeight.Bold
				)

				IconButton(modifier = Modifier.constrainAs(configIconC) {
					linkTo(top = parent.top, bottom = parent.bottom)
					end.linkTo(parent.end)
				}, onClick = { appBarViewModel.onSettingsButtonClick() }) {
					Icon(Icons.Filled.Settings, null, tint = Color.White)
				}
			}
		})
}