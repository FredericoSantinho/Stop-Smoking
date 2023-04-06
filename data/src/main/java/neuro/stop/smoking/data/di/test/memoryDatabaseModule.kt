package neuro.stop.smoking.data.di.test

import androidx.room.Room
import neuro.stop.smoking.data.StopSmokingDatabase
import org.koin.dsl.module

val memoryDatabaseModule = module {
	single {
		Room.inMemoryDatabaseBuilder(
			get(), StopSmokingDatabase::class.java
		).allowMainThreadQueries().build()
	}
}
