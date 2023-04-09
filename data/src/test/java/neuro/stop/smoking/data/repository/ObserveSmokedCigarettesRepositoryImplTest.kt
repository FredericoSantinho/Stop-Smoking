package neuro.stop.smoking.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import neuro.stop.smoking.data.test.mocks.roomSmokedCigaretteMockList
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ObserveSmokedCigarettesRepositoryImplTest {
	@Test
	fun test() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val observeSmokedCigarettesRepository =
			ObserveSmokedCigarettesRepositoryImpl(smokedCigaretteDao)

		whenever(smokedCigaretteDao.observeSmokedCigarettes(0, Long.MAX_VALUE)).thenReturn(flow {
			emit(
				roomSmokedCigaretteMockList()
			)
		})

		val smokedCigaretteDtoList =
			observeSmokedCigarettesRepository.observeSmokedCigarettes(0).first()

		assertEquals(smokedCigaretteDtoMockList(), smokedCigaretteDtoList)
	}
}