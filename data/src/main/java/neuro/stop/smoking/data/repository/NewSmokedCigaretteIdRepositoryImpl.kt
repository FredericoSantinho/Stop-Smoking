package neuro.stop.smoking.data.repository

import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.domain.repository.NewSmokedCigaretteIdRepository

class NewSmokedCigaretteIdRepositoryImpl(private val smokedCigaretteDao: SmokedCigaretteDao) :
	NewSmokedCigaretteIdRepository {
	override suspend fun newSmokedCigaretteId(): Long {
		return (smokedCigaretteDao.getLastSmokedCigaretteId() ?: 0) + 1
	}
}