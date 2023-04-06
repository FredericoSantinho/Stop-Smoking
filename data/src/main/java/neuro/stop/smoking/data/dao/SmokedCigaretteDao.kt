package neuro.stop.smoking.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import neuro.stop.smoking.data.model.RoomSmokedCigarette

@Dao
interface SmokedCigaretteDao {
	@Query("SELECT MAX(smokedCigaretteId) FROM smoked_cigarette_table")
	suspend fun getLastSmokedCigaretteId(): Long?

	@Query("select * from smoked_cigarette_table where timestamp between :initialTimestamp and :finalTimestamp order by timestamp desc")
	fun observeSmokedCigarettes(
		initialTimestamp: Long,
		finalTimestamp: Long
	): Flow<List<RoomSmokedCigarette>>

	@Insert(onConflict = OnConflictStrategy.ABORT)
	suspend fun insert(smokedCigarette: RoomSmokedCigarette): Long

	@Query("delete from smoked_cigarette_table where smokedCigaretteId=:smokedCigaretteId")
	suspend fun delete(smokedCigaretteId: Long)
}