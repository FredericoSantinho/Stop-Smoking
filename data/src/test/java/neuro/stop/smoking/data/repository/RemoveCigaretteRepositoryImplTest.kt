package neuro.stop.smoking.data.repository

import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.SmokedCigaretteDao
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class RemoveCigaretteRepositoryImplTest {

	@Test
	fun test() = runTest {
		val smokedCigaretteDao = mock<SmokedCigaretteDao>()

		val removeCigaretteRepository = RemoveCigaretteRepositoryImpl(smokedCigaretteDao)

		verifyNoInteractions(smokedCigaretteDao)

		removeCigaretteRepository.removeCigarette(1)

		verify(smokedCigaretteDao, times(1)).delete(1)
	}
}