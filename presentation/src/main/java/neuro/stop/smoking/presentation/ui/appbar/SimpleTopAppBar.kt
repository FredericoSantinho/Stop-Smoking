package neuro.stop.smoking.presentation.ui.appbar

import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopAppBar(title: String) {
	TopAppBar(
		elevation = 4.dp,
		title = { },
		backgroundColor = MaterialTheme.colors.primary,
		actions = {
			Row(horizontalArrangement = Start) {
				Text(
					title,
					color = Color.White,
					style = MaterialTheme.typography.h6,
					fontWeight = FontWeight.Bold,
					modifier = Modifier
						.fillMaxWidth()
						.testTag(SimpleTopAppBarTags.TOP_APP_BAR_TITLE)
				)
			}
		},
		modifier = Modifier.testTag(SimpleTopAppBarTags.TOP_APP_BAR)
	)
}

class SimpleTopAppBarTags {
	companion object {
		const val TOP_APP_BAR = "topAppBar"
		const val TOP_APP_BAR_TITLE = "topAppBarTitle"
	}
}