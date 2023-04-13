package neuro.stop.smoking.data.repository

import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.mapper.toData
import neuro.stop.smoking.domain.dto.StartOfDayDto
import neuro.stop.smoking.domain.repository.SetStartOfDayRepository

class SetStartOfDayRepositoryImpl(private val startOfDayDao: StartOfDayDao) :
	SetStartOfDayRepository {
	override suspend fun setStartOfDay(startOfDayDto: StartOfDayDto) {
		startOfDayDao.insert(startOfDayDto.toData())
	}
}