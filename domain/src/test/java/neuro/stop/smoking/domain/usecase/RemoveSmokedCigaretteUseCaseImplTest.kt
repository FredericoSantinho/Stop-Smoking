package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.repository.RemoveCigaretteRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class RemoveSmokedCigaretteUseCaseImplTest {
	@Test
	fun test() = runTest {
		val removeCigaretteRepository = mock<RemoveCigaretteRepository>()

		val removeCigaretteUseCase = RemoveSmokedCigaretteUseCaseImpl(removeCigaretteRepository)

		verifyNoInteractions(removeCigaretteRepository)

		removeCigaretteUseCase.removeSmokedCigarette(1)

		verify(removeCigaretteRepository, times(1)).removeCigarette(1)
	}
}