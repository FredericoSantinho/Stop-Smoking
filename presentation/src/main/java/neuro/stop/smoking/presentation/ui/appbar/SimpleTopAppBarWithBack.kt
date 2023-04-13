package neuro.stop.smoking.presentation.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopAppBarWithBack(
	title: String,
	onBackButtonClick: () -> Unit
) {
	TopAppBar(
		elevation = 4.dp,
		title = {
			Text(
				text = title,
				modifier = Modifier.testTag(SimpleTopAppBarWithBackTags.TOP_APP_BAR_TITLE)
			)
		},
		backgroundColor = MaterialTheme.colors.primary,
		navigationIcon = {
			IconButton(onClick = onBackButtonClick) {
				Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
			}
		}, modifier = Modifier.testTag(SimpleTopAppBarWithBackTags.TOP_APP_BAR)
	)
}

class SimpleTopAppBarWithBackTags {
	companion object {
		const val TOP_APP_BAR = "topAppBar"
		const val TOP_APP_BAR_TITLE = "topAppBarTitle"
	}
}
