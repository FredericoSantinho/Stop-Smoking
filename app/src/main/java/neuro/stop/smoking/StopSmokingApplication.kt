package neuro.stop.smoking

import android.app.Application
import neuro.stop.smoking.data.di.daoModule
import neuro.stop.smoking.data.di.databaseModule
import neuro.stop.smoking.data.di.repositoryModule
import neuro.stop.smoking.presentation.di.presentationMappersModule
import neuro.stop.smoking.presentation.di.presentationModule
import neuro.stop.smoking.presentation.di.useCaseModule
import neuro.stop.smoking.presentation.di.viewModelModule
import neuro.stop.smoking.presentation.viewmodel.application.StopSmokingApplicationViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StopSmokingApplication : Application() {

	private val applicationViewModel: StopSmokingApplicationViewModel by inject()

	override fun onCreate() {
		super.onCreate()

		startKoin {
			modules(viewModelModule)
			modules(presentationModule)
			modules(useCaseModule)
			modules(repositoryModule)
			modules(daoModule)
			modules(databaseModule)
			modules(presentationMappersModule)
			androidContext(this@StopSmokingApplication)
		}

		applicationViewModel.onCreate()
	}
}