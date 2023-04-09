package neuro.stop.smoking.data.repository

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.data.test.mocks.roomSmokedCigaretteMock
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMock
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteError
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SaveSmokedCigaretteRepositoryImplTest {
	@Test
	fun test() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val saveSmokedCigaretteRepository = SaveSmokedCigaretteRepositoryImpl(smokedCigaretteDao)

		val roomSmokedCigarette = roomSmokedCigaretteMock()
		val smokedCigaretteDto = smokedCigaretteDtoMock()

		whenever(smokedCigaretteDao.insert(roomSmokedCigarette)).thenReturn(1L)

		saveSmokedCigaretteRepository.saveSmokedCigarette(smokedCigaretteDto)

		verify(smokedCigaretteDao, times(1)).insert(roomSmokedCigarette)
	}

	@Test(expected = SaveSmokedCigaretteError::class)
	fun testSaveSmokedCigaretteError() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val saveSmokedCigaretteRepository = SaveSmokedCigaretteRepositoryImpl(smokedCigaretteDao)

		val roomSmokedCigarette = roomSmokedCigaretteMock()
		val smokedCigaretteDto = smokedCigaretteDtoMock()

		whenever(smokedCigaretteDao.insert(roomSmokedCigarette)).thenThrow(SQLiteConstraintException())

		saveSmokedCigaretteRepository.saveSmokedCigarette(smokedCigaretteDto)

		verify(smokedCigaretteDao, times(1)).insert(roomSmokedCigarette)
	}

	@Test(expected = IllegalStateException::class)
	fun testGenericError() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val saveSmokedCigaretteRepository = SaveSmokedCigaretteRepositoryImpl(smokedCigaretteDao)

		val roomSmokedCigarette = roomSmokedCigaretteMock(1)
		val smokedCigaretteDto = smokedCigaretteDtoMock(1)

		whenever(smokedCigaretteDao.insert(roomSmokedCigarette)).thenThrow(IllegalStateException())

		saveSmokedCigaretteRepository.saveSmokedCigarette(smokedCigaretteDto)

		verify(smokedCigaretteDao, times(1)).insert(roomSmokedCigarette)
	}
}