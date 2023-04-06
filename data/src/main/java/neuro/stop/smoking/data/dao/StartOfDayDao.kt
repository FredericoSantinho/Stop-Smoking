package neuro.stop.smoking.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.data.model.RoomStartOfDay

@Dao
interface StartOfDayDao {
	@Query("SELECT * FROM start_of_day_table WHERE startOfDayId=1")
	suspend fun getStartOfDay(): RoomStartOfDay?

	@Query("SELECT * FROM start_of_day_table WHERE startOfDayId=1")
	fun observeStartOfDay(): Flow<RoomStartOfDay?>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(roomStartOfDay: RoomStartOfDay): Long

	@Query("delete from start_of_day_table where startOfDayId=1")
	suspend fun delete()
}