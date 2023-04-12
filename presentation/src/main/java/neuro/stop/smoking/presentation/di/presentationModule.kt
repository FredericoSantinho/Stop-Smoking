package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivity
import neuro.stop.smoking.presentation.ui.daily.cigarettes.details.NavigateToDailyCigarettesDetailsActivityImpl
import org.koin.dsl.module

val presentationModule = module {
	factory<NavigateToDailyCigarettesDetailsActivity> { NavigateToDailyCigarettesDetailsActivityImpl() }
}
