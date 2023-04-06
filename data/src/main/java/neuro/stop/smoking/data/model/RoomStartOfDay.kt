package neuro.stop.smoking.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "start_of_day_table")
data class RoomStartOfDay(
	@PrimaryKey
	val startOfDayId: Long,
	val hour: Int,
	val minute: Int,
)