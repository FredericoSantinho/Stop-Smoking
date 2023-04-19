package neuro.stop.smoking.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import neuro.stop.smoking.presentation.model.SmokedCigaretteModel
import neuro.stop.smoking.presentation.ui.theme.StopSmokingTheme

@Composable
fun SmokedCigarettesComposable(
	smokedCigarettes: State<List<SmokedCigaretteModel>>,
	cigaretteUrl: State<String>,
	modifier: Modifier = Modifier,
	onRemoveCigaretteClick: (smokedCigaretteId: Long) -> Unit
) {
	val listState = rememberLazyListState()

	LazyColumn(
		modifier,
		listState,
		reverseLayout = true
	) {
		items(smokedCigarettes.value,
			{ smokedCigaretteModel: SmokedCigaretteModel -> smokedCigaretteModel.smokedCigaretteId }) { smokedCigaretteModel ->
			Column {
				SmokedCigaretteComposable(
					smokedCigaretteModel,
					cigaretteUrl.value
				) { onRemoveCigaretteClick(it) }
			}
		}
	}

	if (smokedCigarettes.value.isNotEmpty()) {
		LaunchedEffect(key1 = smokedCigarettes.value[0]) {
			listState.animateScrollToItem(0)
		}
	}
}

class SmokedCigarettesComposableTags {
	companion object {
		const val SMOKED_CIGARETTE_DIVIDER = "smokedCigaretteDivider"
	}
}

@Preview
@Composable
fun PreviewSmokedCigarettesComposable() {
	val smokedCigarettes = remember {
		mutableStateOf(
			listOf(
				SmokedCigaretteModel(1, "12h13"),
				SmokedCigaretteModel(2, "12h34"),
				SmokedCigaretteModel(3, "13h12"),
				SmokedCigaretteModel(4, "14h00"),
				SmokedCigaretteModel(5, "14h43")
			)
		)
	}
	val cigaretteUrl: State<String> = remember {
		mutableStateOf("https://png.pngtree.com/png-clipart/20191121/original/pngtree-cigarette-icon-cartoon-style-png-image_5146531.jpg")
	}

	StopSmokingTheme {
		SmokedCigarettesComposable(smokedCigarettes, cigaretteUrl) {}
	}
}
