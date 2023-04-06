package neuro.stop.smoking.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

@Composable
fun SmokedCigaretteComposable(
	smokedCigaretteModel: SmokedCigaretteModel,
	cigaretteUrl: String,
	modifier: Modifier = Modifier,
	onRemoveCigaretteClick: (smokedCigaretteId: Long) -> Unit
) {
	ConstraintLayout(
		modifier = modifier
			.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_COMPOSABLE + smokedCigaretteModel.smokedCigaretteId)
			.fillMaxWidth()
	) {
		val (cigaretteImageC, smokedACigaretteAtC, removeC) = createRefs()

		Image(
			modifier = Modifier
				.constrainAs(cigaretteImageC) {
					linkTo(parent.top, parent.bottom)
					start.linkTo(parent.start, margin = 16.dp)
				}
				.size(24.dp)
				.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_IMAGE + smokedCigaretteModel.smokedCigaretteId),
			painter = rememberAsyncImagePainter(cigaretteUrl),
			contentDescription = null,
			contentScale = ContentScale.Crop
		)
		Row(Modifier
			.constrainAs(smokedACigaretteAtC) {
				linkTo(parent.start, parent.end)
				linkTo(parent.top, parent.bottom)
			}
		) {
			Text(
				modifier = Modifier
					.padding(start = 16.dp)
					.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TEXT + smokedCigaretteModel.smokedCigaretteId),
				text = stringResource(id = R.string.smoked_a_cigarette_at)
			)
			Text(
				modifier = Modifier
					.padding(start = 16.dp)
					.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_AT_TIME + smokedCigaretteModel.smokedCigaretteId),
				text = smokedCigaretteModel.time
			)
		}
		IconButton(
			onClick = {
				onRemoveCigaretteClick(smokedCigaretteModel.smokedCigaretteId)
			},
			modifier = Modifier
				.constrainAs(removeC) {
					end.linkTo(parent.end)
				}
				.padding(start = 8.dp)
				.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON_BUTTON + smokedCigaretteModel.smokedCigaretteId)
		) {
			Icon(
				modifier = Modifier.testTag(SmokedCigaretteComposableTags.SMOKED_CIGARETTE_REMOVE_ICON + smokedCigaretteModel.smokedCigaretteId),
				painter = painterResource(id = R.drawable.ic_remove_smoked_cigarette_24),
				contentDescription = null,
				tint = MaterialTheme.colors.onBackground
			)
		}
	}
}

class SmokedCigaretteComposableTags {
	companion object {
		const val SMOKED_CIGARETTE_COMPOSABLE = "smokedCigaretteComposable"
		const val SMOKED_CIGARETTE_IMAGE = "smokedCigaretteImage"
		const val SMOKED_CIGARETTE_AT_TEXT = "smokedCigaretteAtText"
		const val SMOKED_CIGARETTE_AT_TIME = "smokedCigaretteAtTime"
		const val SMOKED_CIGARETTE_REMOVE_ICON_BUTTON = "smokedCigaretteRemoveIconButton"
		const val SMOKED_CIGARETTE_REMOVE_ICON = "smokedCigaretteRemoveIcon"
	}
}

@Preview
@Composable
fun PreviewSmokedCigaretteComposable() {
	val smokedCigaretteModel = SmokedCigaretteModel(1, "12h23")

	val cigaretteUrl =
		"https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg"

	StopSmokingTheme {
		SmokedCigaretteComposable(smokedCigaretteModel, cigaretteUrl = cigaretteUrl) { }
	}
}
