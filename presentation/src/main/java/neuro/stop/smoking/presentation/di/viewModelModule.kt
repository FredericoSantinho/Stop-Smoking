package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModel
import org.koin.dsl.module

val viewModelModule = module {
	single { AppBarViewModel() }
}
