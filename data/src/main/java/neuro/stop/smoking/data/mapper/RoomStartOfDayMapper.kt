package neuro.stop.smoking.data.mapper

import neuro.stop.smoking.data.model.RoomStartOfDay
import neuro.stop.smoking.domain.dto.StartOfDayDto

fun RoomStartOfDay.toDomain() = StartOfDayDto(hour, minute)

fun StartOfDayDto.toData() = RoomStartOfDay(1, hour, minute)