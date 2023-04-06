package neuro.stop.smoking.data.repository

import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.domain.repository.RemoveCigaretteRepository

class RemoveCigaretteRepositoryImpl(private val smokedCigaretteDao: SmokedCigaretteDao) :
	RemoveCigaretteRepository {
	override suspend fun removeCigarette(smokedCigaretteId: Long) {
		smokedCigaretteDao.delete(smokedCigaretteId)
	}
}