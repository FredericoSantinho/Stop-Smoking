package neuro.stop.smoking.data.repository

import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class NewSmokedCigaretteIdRepositoryImplTest {
	@Test
	fun testAbsentLastSmokedCigaretteId() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val newSmokedCigaretteIdRepository = NewSmokedCigaretteIdRepositoryImpl(smokedCigaretteDao)

		whenever(smokedCigaretteDao.getLastSmokedCigaretteId()).thenReturn(null)

		assertEquals(1L, newSmokedCigaretteIdRepository.newSmokedCigaretteId())
	}

	@Test
	fun testPresentLastSmokedCigaretteId() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val newSmokedCigaretteIdRepository = NewSmokedCigaretteIdRepositoryImpl(smokedCigaretteDao)

		whenever(smokedCigaretteDao.getLastSmokedCigaretteId()).thenReturn(1L)

		assertEquals(2L, newSmokedCigaretteIdRepository.newSmokedCigaretteId())
	}
}