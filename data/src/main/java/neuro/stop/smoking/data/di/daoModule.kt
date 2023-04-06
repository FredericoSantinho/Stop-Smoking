package neuro.stop.smoking.data.di

import neuro.stop.smoking.data.StopSmokingDatabase
import org.koin.dsl.module

val daoModule = module {
	factory { get<StopSmokingDatabase>().smokedCigaretteDao }
	factory { get<StopSmokingDatabase>().startOfDayDao }
}
