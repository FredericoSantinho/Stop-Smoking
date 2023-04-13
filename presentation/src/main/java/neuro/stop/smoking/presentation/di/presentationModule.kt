package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.ui.common.datetime.DefaultShowTimePicker
import neuro.stop.smoking.presentation.ui.common.datetime.ShowTimePicker
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivityImpl
import neuro.stop.smoking.presentation.ui.settings.NavigateToSettings
import neuro.stop.smoking.presentation.ui.settings.NavigateToSettingsImpl
import neuro.stop.smoking.presentation.ui.settings.items.NavigateToChangeStartOfDay
import neuro.stop.smoking.presentation.ui.settings.items.NavigateToChangeStartOfDayImpl
import org.koin.dsl.module

val presentationModule = module {
	factory<NavigateToSettings> { NavigateToSettingsImpl() }
	factory<ShowTimePicker> { DefaultShowTimePicker() }
	factory<NavigateToDailyCigarettesDetailsActivity> { NavigateToDailyCigarettesDetailsActivityImpl() }
	factory<NavigateToChangeStartOfDay> { NavigateToChangeStartOfDayImpl() }
}
