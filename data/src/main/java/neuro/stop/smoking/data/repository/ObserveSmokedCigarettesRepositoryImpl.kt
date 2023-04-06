package neuro.stop.smoking.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.data.mapper.toDomain
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.repository.ObserveSmokedCigarettesRepository

class ObserveSmokedCigarettesRepositoryImpl(private val smokedCigaretteDao: SmokedCigaretteDao) :
	ObserveSmokedCigarettesRepository {
	override fun observeSmokedCigarettes(
		initialTimestamp: Long,
		finalTimestamp: Long
	): Flow<List<SmokedCigaretteDto>> {
		return smokedCigaretteDao.observeSmokedCigarettes(initialTimestamp, finalTimestamp)
			.map { it.toDomain() }.distinctUntilChanged()
	}
}