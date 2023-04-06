package neuro.stop.smoking.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "smoked_cigarette_table",
	indices = [Index(value = ["timestamp"], unique = false)]
)
data class RoomSmokedCigarette(
	@PrimaryKey
	val smokedCigaretteId: Long,
	val timestamp: Long,
)