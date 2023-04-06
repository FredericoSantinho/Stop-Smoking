package neuro.stop.smoking.data.di

import androidx.room.Room
import neuro.stop.smoking.data.StopSmokingDatabase
import org.koin.dsl.module

private const val DATABASE_NAME = "stop_smoking"

val databaseModule = module {
	single { Room.databaseBuilder(get(), StopSmokingDatabase::class.java, DATABASE_NAME).build() }
}
