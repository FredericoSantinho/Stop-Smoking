package neuro.stop.smoking.presentation.ui.settings

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.ui.appbar.SimpleTopAppBarWithBack
import neuro.stop.smoking.presentation.ui.appbar.toPresentation
import neuro.stop.smoking.presentation.ui.settings.items.NavigateToChangeStartOfDay
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.settings.DummySettingsViewModel
import neuro.stop.smoking.presentation.viewmodel.settings.SettingsViewModel
import neuro.stop.smoking.presentation.viewmodel.settings.SettingsViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.settings.UiEvent
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsComposable(
	settingsViewModel: SettingsViewModel = getViewModel<SettingsViewModelImpl>(),
	navigateToChangeStartOfDay: NavigateToChangeStartOfDay = koinInject()
) {
	val uiEvent by settingsViewModel.uiEvent

	val scrollState = rememberScrollState()

	Column {
		SimpleTopAppBarWithBack(title = settingsViewModel.title.value.toPresentation()) {
			settingsViewModel.onBackClick()
		}
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(scrollState)
		) {
			Column(modifier = Modifier
				.clickable {
					settingsViewModel.onChangeStartOfDayClick()
				}
				.testTag(SettingsComposableTags.SETTINGS_CHANGE_START_OF_DAY)) {
				Row(Modifier.padding(16.dp)) {
					Text(
						text = stringResource(id = R.string.change_start_of_day),
						style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onBackground),
						modifier = Modifier.testTag(SettingsComposableTags.SETTINGS_CHANGE_START_OF_DAY_TEXT)
					)
				}
				Divider(
					modifier = Modifier
						.padding(start = 4.dp, end = 4.dp),
					thickness = 1.dp,
					color = Color.Gray
				)
			}
		}
	}

	onUiEvent(uiEvent, navigateToChangeStartOfDay, settingsViewModel)
}

@Composable
fun onUiEvent(
	uiEvent: UiEvent?,
	navigateToChangeStartOfDay: NavigateToChangeStartOfDay,
	settingsViewModel: SettingsViewModel
) {
	when (uiEvent) {
		UiEvent.NavigateBack -> {
			(LocalContext.current as Activity).finish()
		}

		UiEvent.NavigateToChangeStartOfDay -> navigateToChangeStartOfDay.navigateToChangeStartOfDayActivity(
			LocalContext.current as Activity
		)

		null -> {}
	}
	settingsViewModel.eventConsumed()
}

class SettingsComposableTags {
	companion object {
		const val SETTINGS_CHANGE_START_OF_DAY = "settingsChangeStartOfDay"
		const val SETTINGS_CHANGE_START_OF_DAY_TEXT = "settingsChangeStartOfDayText"
	}
}

@Preview
@Composable
fun PreviewSettingsComposable() {
	val navigateToChangeStartOfDay = object : NavigateToChangeStartOfDay {
		override fun navigateToChangeStartOfDayActivity(context: Context) {
		}
	}

	StopSmokingTheme {
		SettingsComposable(DummySettingsViewModel(), navigateToChangeStartOfDay)
	}
}
