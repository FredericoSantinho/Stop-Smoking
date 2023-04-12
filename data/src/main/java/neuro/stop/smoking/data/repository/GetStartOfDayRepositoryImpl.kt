package neuro.stop.smoking.data.repository

import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.mapper.toDomain
import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.GetStartOfDayRepository

class GetStartOfDayRepositoryImpl(private val startOfDayDao: StartOfDayDao) :
	GetStartOfDayRepository {
	override suspend fun getStartOfDay(): StartOfDayDto? {
		return startOfDayDao.getStartOfDay()?.toDomain()
	}
}