package neuro.stop.smoking.presentation.ui.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.ui.daily.cigarettes.DailyCigarettesTitle
import neuro.stop.smoking.presentation.ui.home.HomeTitle
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.appbar.Title.EmptyTitle

@Composable
fun Title.toPresentation(): String {
	return when (this) {
		is EmptyTitle -> ""
		is HomeTitle -> stringResource(id = R.string.home_title)
		is DailyCigarettesTitle -> stringResource(id = R.string.daily_cigarettes_title)
		else -> {
			throw java.lang.IllegalArgumentException("Mapping not implemented for class ${this.javaClass}!")
		}
	}
}