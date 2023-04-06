package neuro.stop.smoking.data

import androidx.room.Database
import androidx.room.RoomDatabase
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.model.RoomSmokedCigarette
import neuro.stop.smoking.data.model.RoomStartOfDay

@Database(
	entities = [RoomSmokedCigarette::class, RoomStartOfDay::class],
	version = 1
)
abstract class StopSmokingDatabase : RoomDatabase() {
	abstract val smokedCigaretteDao: SmokedCigaretteDao
	abstract val startOfDayDao: StartOfDayDao
}
