package neuro.stop.smoking

import android.app.Application
import neuro.stop.smoking.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StopSmokingApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			modules(viewModelModule)
			androidContext(this@StopSmokingApplication)
		}
	}
}