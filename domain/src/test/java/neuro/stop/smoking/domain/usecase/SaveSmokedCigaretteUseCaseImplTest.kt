package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import neuro.stop.smoking.domain.repository.NewSmokedCigaretteIdRepository
import neuro.stop.smoking.domain.repository.SaveSmokedCigaretteRepository
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMock
import org.junit.Test
import org.mockito.kotlin.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SaveSmokedCigaretteUseCaseImplTest {

	@Test
	fun test() = runTest {
		val newSmokedCigaretteIdRepository = mock<NewSmokedCigaretteIdRepository>()
		val saveSmokedCigaretteRepository = mock<SaveSmokedCigaretteRepository>()

		val saveSmokedCigaretteUseCase =
			SaveSmokedCigaretteUseCaseImpl(newSmokedCigaretteIdRepository, saveSmokedCigaretteRepository)

		verifyNoInteractions(newSmokedCigaretteIdRepository)
		verifyNoInteractions(saveSmokedCigaretteRepository)

		val smokedCigaretteDto = smokedCigaretteDtoMock(1, 12)

		whenever(newSmokedCigaretteIdRepository.newSmokedCigaretteId()).thenReturn(1)
		whenever(saveSmokedCigaretteRepository.saveSmokedCigarette(smokedCigaretteDto)).thenReturn(1)

		saveSmokedCigaretteUseCase.saveSmokedCigaretteUseCase(12)

		verify(newSmokedCigaretteIdRepository, times(1)).newSmokedCigaretteId()
		verify(saveSmokedCigaretteRepository, times(1)).saveSmokedCigarette(smokedCigaretteDto)
	}

	@Test(expected = SaveSmokedCigaretteError::class)
	fun testError() = runTest {
		val newSmokedCigaretteIdRepository = mock<NewSmokedCigaretteIdRepository>()
		val saveSmokedCigaretteRepository = mock<SaveSmokedCigaretteRepository>()

		val saveSmokedCigaretteUseCase =
			SaveSmokedCigaretteUseCaseImpl(newSmokedCigaretteIdRepository, saveSmokedCigaretteRepository)

		verifyNoInteractions(saveSmokedCigaretteRepository)

		val smokedCigaretteDto = smokedCigaretteDtoMock(1, 12)

		whenever(newSmokedCigaretteIdRepository.newSmokedCigaretteId()).thenReturn(1)
		whenever(saveSmokedCigaretteRepository.saveSmokedCigarette(smokedCigaretteDto)).thenThrow(
			SaveSmokedCigaretteError()
		)

		saveSmokedCigaretteUseCase.saveSmokedCigaretteUseCase(12)

		verify(newSmokedCigaretteIdRepository, times(1)).newSmokedCigaretteId()
		verify(saveSmokedCigaretteRepository, times(1)).saveSmokedCigarette(smokedCigaretteDto)
	}
}