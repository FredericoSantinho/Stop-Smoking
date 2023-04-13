package neuro.stop.smoking.presentation.ui.settings.items

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.mapper.toUi
import neuro.stop.smoking.presentation.ui.appbar.SimpleTopAppBarWithBack
import neuro.stop.smoking.presentation.ui.appbar.toPresentation
import neuro.stop.smoking.presentation.ui.common.datetime.DefaultShowTimePicker
import neuro.stop.smoking.presentation.ui.common.datetime.ShowTimePicker
import neuro.stop.smoking.presentation.ui.common.dialog.ErrorDialog
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapper
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapperImpl
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.ChangeStartOfDayViewModel
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.ChangeStartOfDayViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.DummyChangeStartOfDayViewModel
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.UiEvent
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.UiState
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
fun ChangeStartOfDayComposable(
	changeStartOfDayViewModel: ChangeStartOfDayViewModel = getViewModel<ChangeStartOfDayViewModelImpl>(),
	showTimePicker: ShowTimePicker = koinInject()
) {
	val uiState by changeStartOfDayViewModel.uiState
	val uiEvent by changeStartOfDayViewModel.uiEvent

	val hour = changeStartOfDayViewModel.hour.value
	val minute = changeStartOfDayViewModel.minute.value

	Column(modifier = Modifier.testTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY)) {
		SimpleTopAppBarWithBack(
			changeStartOfDayViewModel.title.value.toPresentation()
		) { changeStartOfDayViewModel.onBackButtonClick() }

		Column(
			Modifier
				.fillMaxSize(), verticalArrangement = Arrangement.Center
		) {
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
				Text(
					text = stringResource(id = R.string.change_start_of_day),
					style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground),
					modifier = Modifier.testTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY_COPY)
				)
			}
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
				Text(
					modifier = Modifier
						.padding(top = 16.dp)
						.clickable { changeStartOfDayViewModel.onTimeClick() }
						.testTag(ChangeStartOfDayComposableTags.CHANGE_START_OF_DAY_TIME),
					text = changeStartOfDayViewModel.timeText.value,
					style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground)
				)
			}
		}
	}

	onUiState(
		uiState,
		showTimePicker,
		changeStartOfDayViewModel,
		hour,
		minute
	)
	onUiEvent(uiEvent, changeStartOfDayViewModel)
}

@Composable
fun onUiEvent(uiEvent: UiEvent?, changeStartOfDayViewModel: ChangeStartOfDayViewModel) {
	when (uiEvent) {
		UiEvent.NavigateBack -> {
			(LocalContext.current as Activity).finish()
		}

		null -> {}
	}
	changeStartOfDayViewModel.eventConsumed()
}

@Composable
fun onUiState(
	uiState: UiState,
	showTimePicker: ShowTimePicker,
	changeStartOfDayViewModel: ChangeStartOfDayViewModel,
	hour: Int,
	minute: Int
) {
	when (uiState) {
		UiState.Ready -> {}
		UiState.ShowTimePicker -> showTimePicker.showTimePicker(
			LocalContext.current,
			hour,
			minute
		) { newHour, newMinute -> changeStartOfDayViewModel.onTimeChange(newHour, newMinute) }

		is UiState.Error -> ErrorDialog(
			uiState.errorDescription.toUi(),
			{ changeStartOfDayViewModel.onErrorDismiss() },
			{ changeStartOfDayViewModel.onErrorOkButtonClick() })
	}
}

class ChangeStartOfDayComposableTags {
	companion object {
		const val CHANGE_START_OF_DAY = "changeStartOfDay"
		const val CHANGE_START_OF_DAY_COPY = "changeStartOfDayCopy"
		const val CHANGE_START_OF_DAY_TIME = "changeStartOfDayTime"
	}
}

@Preview
@Composable
fun PreviewChangeStartOfDayComposable() {
	val changeStartOfDayViewModel: ChangeStartOfDayViewModel = DummyChangeStartOfDayViewModel()
	val timeTextMapper: TimeTextMapper = TimeTextMapperImpl()
	val showTimePicker: ShowTimePicker = DefaultShowTimePicker()

	StopSmokingTheme {
		ChangeStartOfDayComposable(changeStartOfDayViewModel, showTimePicker)
	}
}
