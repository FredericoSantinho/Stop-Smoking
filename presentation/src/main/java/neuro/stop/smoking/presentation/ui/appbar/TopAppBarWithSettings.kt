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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import neuro.stop.smoking.presentation.ui.appbar.TopAppBarWithSettingsTags.Companion.TOP_APP_BAR
import neuro.stop.smoking.presentation.ui.appbar.TopAppBarWithSettingsTags.Companion.TOP_APP_BAR_TITLE
import neuro.stop.smoking.presentation.viewmodel.appbar.Title

@Composable
fun TopAppBarWithSettings(
	title: State<Title>, onNavigateUp: () -> Unit, onSettingButton: () -> Unit
) {
	androidx.compose.material.TopAppBar(
		elevation = 4.dp,
		title = { },
		backgroundColor = MaterialTheme.colors.primary,
		navigationIcon = {
			IconButton(onClick = onNavigateUp) {
				Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
			}
		},
		actions = {
			ConstraintLayout(Modifier.fillMaxSize()) {
				val (titleC, configIconC) = createRefs()

				Text(
					title.value.toPresentation(),
					Modifier
						.constrainAs(titleC) {
							linkTo(top = parent.top, bottom = parent.bottom)
						}
						.testTag(TOP_APP_BAR_TITLE),
					color = Color.White,
					style = MaterialTheme.typography.h6,
					fontWeight = FontWeight.Bold
				)

				IconButton(modifier = Modifier.constrainAs(configIconC) {
					linkTo(top = parent.top, bottom = parent.bottom)
					end.linkTo(parent.end)
				}, onClick = onSettingButton) {
					Icon(Icons.Filled.Settings, null, tint = Color.White)
				}
			}
		}, modifier = Modifier.testTag(TOP_APP_BAR)
	)
}

class TopAppBarWithSettingsTags {
	companion object {
		const val TOP_APP_BAR = "topAppBar"
		const val TOP_APP_BAR_TITLE = "topAppBarTitle"
	}
}