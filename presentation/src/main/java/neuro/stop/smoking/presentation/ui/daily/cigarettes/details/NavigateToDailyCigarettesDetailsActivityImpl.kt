package neuro.stop.smoking.presentation.ui.daily.cigarettes.details

import android.content.Context

class NavigateToDailyCigarettesDetailsActivityImpl : NavigateToDailyCigarettesDetailsActivity {
	override fun navigateToDailyCigarettesDetailsActivity(context: Context, date: String) {
		DailyCigarettesDetailsActivity.start(context, date)
	}
}