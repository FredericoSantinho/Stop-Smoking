package neuro.stop.smoking.presentation.ui.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import neuro.stop.smoking.presentation.R
import neuro.stop.smoking.presentation.viewmodel.appbar.Title
import neuro.stop.smoking.presentation.viewmodel.appbar.Title.EmptyTitle
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesTitle
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsTitle
import neuro.stop.smoking.presentation.viewmodel.home.HomeTitle

@Composable
fun Title.toPresentation(): String {
	return when (this) {
		is EmptyTitle -> ""
		is HomeTitle -> stringResource(id = R.string.home_title)
		is DailyCigarettesTitle -> stringResource(id = R.string.daily_cigarettes_title)
		is DailyCigarettesDetailsTitle -> stringResource(
			id = R.string.daily_cigarettes_details_title,
			date
		)

		else -> {
			throw java.lang.IllegalArgumentException("Mapping not implemented for class ${this.javaClass}!")
		}
	}
}