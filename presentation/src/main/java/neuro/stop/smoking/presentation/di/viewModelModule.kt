package neuro.stop.smoking.presentation.di

import neuro.stop.smoking.presentation.viewmodel.appbar.AppBarViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.application.StopSmokingApplicationViewModel
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapper
import neuro.stop.smoking.presentation.viewmodel.common.datetime.TimeTextMapperImpl
import neuro.stop.smoking.presentation.viewmodel.common.formatter.NumberFormater
import neuro.stop.smoking.presentation.viewmodel.common.formatter.NumberFormaterImpl
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.DailyCigarettesViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.daily.cigarettes.details.DailyCigarettesDetailsViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.home.HomeViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.main.MainViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.settings.SettingsViewModelImpl
import neuro.stop.smoking.presentation.viewmodel.settings.change.start.day.ChangeStartOfDayViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	factory { StopSmokingApplicationViewModel(get(), get()) }
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
	viewModel { DailyCigarettesViewModelImpl(get(), get<AppBarViewModelImpl>()) }
	viewModel { ChangeStartOfDayViewModelImpl(get(), get(), get()) }
	factory<TimeTextMapper> { TimeTextMapperImpl() }
	factory<NumberFormater> { NumberFormaterImpl() }
	viewModel { DailyCigarettesDetailsViewModelImpl(get(), get(), get(), get(), get()) }
	viewModel { SettingsViewModelImpl() }
}
