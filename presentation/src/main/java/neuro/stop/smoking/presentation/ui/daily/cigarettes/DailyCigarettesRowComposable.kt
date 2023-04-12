package neuro.stop.smoking.presentation.ui.daily.cigarettes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay

@Composable
fun DailyCigarettesRowComposable(
	smokedCigarettesPerDay: SmokedCigarettesPerDay,
	modifier: Modifier = Modifier,
	onSmokedCigarettesPerDayClick: (smokedCigarettesPerDay: String) -> Unit
) {
	Card(modifier = modifier
		.clickable {
			onSmokedCigarettesPerDayClick(smokedCigarettesPerDay.date)
		}
		.testTag(DailyCigarettesRowComposableTags.DAILY_CIGARETTES_ROW_CARD + smokedCigarettesPerDay.date)) {
		Column(modifier = Modifier.padding(8.dp)) {
			Row(
				modifier = Modifier
					.fillMaxWidth(), horizontalArrangement = Arrangement.Center
			) {
				Text(
					text = smokedCigarettesPerDay.date,
					modifier = Modifier.testTag(DailyCigarettesRowComposableTags.DAILY_CIGARETTES_ROW_DATE + smokedCigarettesPerDay.date)
				)
			}
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
				Text(
					text = smokedCigarettesPerDay.smokedCigarettes.size.toString(),
					modifier = Modifier.testTag(DailyCigarettesRowComposableTags.DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER + smokedCigarettesPerDay.date)
				)
			}
		}
	}
}

class DailyCigarettesRowComposableTags {
	companion object {
		const val DAILY_CIGARETTES_ROW_CARD = "dailyCigarettesRowComposableTags"
		const val DAILY_CIGARETTES_ROW_DATE = "dailyCigarettesRowDate"
		const val DAILY_CIGARETTES_ROW_SMOKED_CIGARETTES_NUMBER =
			"dailyCigarettesRowSmokedCigarettesNumber"
	}
}

@Preview
@Composable
fun PreviewDailySmokedCigarettesComposable() {
	val smokedCigarettesPerDay = SmokedCigarettesPerDay("2023-05-22", smokedCigaretteDtoMockList())
	val onSmokedCigarettesPerDayClick: (smokedCigarettesPerDay: String) -> Unit = {}

	StopSmokingTheme {
		DailyCigarettesRowComposable(
			smokedCigarettesPerDay,
			onSmokedCigarettesPerDayClick = onSmokedCigarettesPerDayClick
		)
	}
}
