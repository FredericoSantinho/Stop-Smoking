package neuro.stop.smoking.presentation.ui.daily.cigarettes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neuro.stop.smoking.presentation.ui.common.compose.rememberUnit
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesViewModel
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesViewModelMock
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.UiEvent
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.model.SmokedCigarettesPerDay
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
fun DailyCigarettesComposable(
	dailyCigarettesViewModel: DailyCigarettesViewModel = getViewModel<DailyCigarettesViewModelImpl>(),
	navigateToDailyCigarettesDetailsActivity: NavigateToDailyCigarettesDetailsActivity = koinInject()
) {
	val uiEvent by dailyCigarettesViewModel.uiEvent

	rememberUnit { dailyCigarettesViewModel.onComposition() }

	LazyColumn(
		reverseLayout = true,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {

		items(dailyCigarettesViewModel.smokedCigarettesPerDay.value,
			{ smokedCigarettesPerDay: SmokedCigarettesPerDay -> smokedCigarettesPerDay.date.hashCode() }) { smokedCigarettesPerDay ->
			DailyCigarettesRowComposable(smokedCigarettesPerDay) {
				dailyCigarettesViewModel.onSmokedCigarettesPerDayClick(it)
			}
		}
	}

	onUiEvent(
		uiEvent,
		dailyCigarettesViewModel,
		navigateToDailyCigarettesDetailsActivity
	)
}

@Composable
fun onUiEvent(
	uiEvent: UiEvent?,
	dailyCigarettesViewModel: DailyCigarettesViewModel,
	navigateToDailyCigarettesDetailsActivity: NavigateToDailyCigarettesDetailsActivity
) {
	when (uiEvent) {
		is UiEvent.NavigateToSmokedCigarettesInDayDetails ->
			navigateToDailyCigarettesDetailsActivity.navigateToDailyCigarettesDetailsActivity(
				LocalContext.current,
				uiEvent.date
			)

		null -> {}
	}
	dailyCigarettesViewModel.eventConsumed()
}

@Preview
@Composable
fun PreviewDailyCigarettesComposable() {
	val dailyCigarettesViewModel: DailyCigarettesViewModel = DailyCigarettesViewModelMock()
	val navigateToDailyCigarettesDetailsActivity: NavigateToDailyCigarettesDetailsActivity =
		object : NavigateToDailyCigarettesDetailsActivity {
			override fun navigateToDailyCigarettesDetailsActivity(context: Context, date: String) {
			}
		}

	StopSmokingTheme {
		DailyCigarettesComposable(
			dailyCigarettesViewModel,
			navigateToDailyCigarettesDetailsActivity
		)
	}
}
