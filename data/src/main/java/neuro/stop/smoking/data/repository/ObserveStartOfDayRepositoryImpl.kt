package neuro.stop.smoking.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.mapper.toDomain
import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.ObserveStartOfDayRepository

class ObserveStartOfDayRepositoryImpl(private val startOfDayDao: StartOfDayDao) :
	ObserveStartOfDayRepository {
	override fun observeStartOfDay(): Flow<StartOfDayDto?> {
		return startOfDayDao.observeStartOfDay().map { it?.toDomain() }
	}
}