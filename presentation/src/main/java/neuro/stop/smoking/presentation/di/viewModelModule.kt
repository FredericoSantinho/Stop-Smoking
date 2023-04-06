package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	single { AppBarViewModelImpl() }
	viewModel { MainViewModelImpl() }
	viewModel {
		HomeViewModelImpl(
			get(),
			get(),
			get(),
			get(),
			get(),
			get(),
			get<AppBarViewModelImpl>()
		)
	}
}
