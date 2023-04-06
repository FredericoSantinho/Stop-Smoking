package neuro.stop.smoking.data.repository

import android.database.sqlite.SQLiteConstraintException
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.data.mapper.toData
import neuro.stop.smoking.domain.dto.SmokedCigaretteDto
import neuro.stop.smoking.domain.repository.SaveSmokedCigaretteRepository
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteError

class SaveSmokedCigaretteRepositoryImpl(private val smokedCigaretteDao: SmokedCigaretteDao) :
	SaveSmokedCigaretteRepository {
	override suspend fun saveSmokedCigarette(smokedCigaretteDto: SmokedCigaretteDto): Long {
		try {
			return smokedCigaretteDao.insert(smokedCigaretteDto.toData())
		} catch (e: SQLiteConstraintException) {
			throw SaveSmokedCigaretteError()
		}
	}
}