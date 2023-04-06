package neuro.stop.smoking.presentation.ui.common.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

@Composable
fun ErrorDialog(
	description: String,
	onErrorDismiss: () -> Unit = {},
	onErrorOkButtonClick: () -> Unit = {}
) {
	AlertDialog(
		onDismissRequest = {
			onErrorDismiss()
		},
		confirmButton = {
			TextButton(
				onClick = {
					onErrorOkButtonClick()
				}
			) {
				Text(
					text = stringResource(id = R.string.ok),
					modifier = Modifier.testTag(ErrorDialogTags.ERROR_DIALOG_BUTTON_TEXT)
				)
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.error_title), modifier = Modifier.testTag(
					ErrorDialogTags.ERROR_DIALOG_TITLE
				)
			)
		},
		text = {
			Text(
				text = description,
				modifier = Modifier.testTag(ErrorDialogTags.ERROR_DIALOG_DESCRIPTION)
			)
		},
		modifier = Modifier
			.fillMaxWidth()
			.padding(32.dp)
			.testTag(ErrorDialogTags.ERROR_DIALOG),
		shape = RoundedCornerShape(5.dp),
		backgroundColor = Color.White
	)
}

class ErrorDialogTags {
	companion object {
		const val ERROR_DIALOG = "alertDialog"
		const val ERROR_DIALOG_TITLE = "errorDialogTitle"
		const val ERROR_DIALOG_DESCRIPTION = "errorDialogDescription"
		const val ERROR_DIALOG_BUTTON_TEXT = "errorDialogButtonText"
	}
}

@Preview
@Composable
fun PreviewErrorDialogComposable() {
	StopSmokingTheme {
		ErrorDialog("this is an error")
	}
}
