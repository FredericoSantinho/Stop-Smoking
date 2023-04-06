package neuro.stop.smoking.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.LAST_CIGARETTE_MINUTES
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.LAST_CIGARETTE_TEXT
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.SMOKED_CIGARETTES_NUMBER
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.SMOKED_CIGARETTES_TEXT
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.SMOKE_BUTTON
import neuro.stop.smoking.presentation.ui.home.HomeFooterComposableTags.Companion.SMOKE_BUTTON_TEXT
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

@Composable
fun HomeFooterComposable(
	smokedCigarettesNumber: State<String>,
	lastCigaretteTimeMinutes: State<String>,
	modifier: Modifier = Modifier,
	onSmokeButtonClick: () -> Unit
) {
	Column(modifier = modifier) {
		Row(
			modifier = modifier
				.padding(16.dp)
				.fillMaxWidth(), horizontalArrangement = Arrangement.Center
		) {
			SmokedCigarettesTodayComposable(smokedCigarettesNumber)
		}
		Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
			LastCigaretteMinutesComposable(
				modifier.padding(16.dp),
				lastCigaretteTimeMinutes,
				onSmokeButtonClick
			)
		}
	}
}

@Composable
fun SmokedCigarettesTodayComposable(smokedCigarettesNumber: State<String>) {
	Text(
		modifier = Modifier.testTag(SMOKED_CIGARETTES_TEXT),
		text = stringResource(id = R.string.smoked_cigarettes_today)
	)
	Text(
		modifier = Modifier
			.padding(start = 16.dp)
			.testTag(SMOKED_CIGARETTES_NUMBER),
		text = smokedCigarettesNumber.value
	)
}

@Composable
fun LastCigaretteMinutesComposable(
	modifier: Modifier,
	lastCigaretteTimeMinutes: State<String>,
	onSmokeButtonClick: () -> Unit
) {
	ConstraintLayout(
		modifier = modifier
	) {
		val (lastCigaretteCopyC, lastCigaretteC, buttonC) = createRefs()

		Text(
			modifier = Modifier
				.constrainAs(lastCigaretteCopyC) {
					linkTo(top = parent.top, bottom = parent.bottom)
					start.linkTo(parent.start)
				}
				.testTag(LAST_CIGARETTE_TEXT), text = stringResource(
				id =
				R.string.last_cigarette_minutes
			)
		)
		Text(
			modifier = Modifier
				.constrainAs(lastCigaretteC) {
					linkTo(top = parent.top, bottom = parent.bottom)
					start.linkTo(lastCigaretteCopyC.end, margin = 16.dp)
				}
				.testTag(LAST_CIGARETTE_MINUTES),
			text = lastCigaretteTimeMinutes.value
		)
		Button(modifier = Modifier
			.constrainAs(buttonC) {
				start.linkTo(lastCigaretteC.end, margin = 16.dp)
			}
			.testTag(SMOKE_BUTTON), onClick = {
			onSmokeButtonClick()
		}) {
			Text(
				modifier = Modifier.testTag(SMOKE_BUTTON_TEXT),
				text = stringResource(id = R.string.smoke)
			)
		}
	}
}

class HomeFooterComposableTags {
	companion object {
		const val SMOKED_CIGARETTES_TEXT = "smokedCigarettesText"
		const val SMOKED_CIGARETTES_NUMBER = "smokedCigarettesNumber"
		const val LAST_CIGARETTE_TEXT = "lastCigaretteText"
		const val LAST_CIGARETTE_MINUTES = "lastCigaretteMinutes"
		const val SMOKE_BUTTON = "smokeButton"
		const val SMOKE_BUTTON_TEXT = "smokeButtonText"
	}
}

@Preview
@Composable
fun PreviewHomeFooterComposable() {
	val smokedCigarettesNumber = remember { mutableStateOf("3") }
	val lastCigaretteTimeMinutes = remember { mutableStateOf("10") }
	val onSmokeButtonClick = {}

	StopSmokingTheme {
		HomeFooterComposable(
			smokedCigarettesNumber,
			lastCigaretteTimeMinutes,
			onSmokeButtonClick = onSmokeButtonClick
		)
	}
}
