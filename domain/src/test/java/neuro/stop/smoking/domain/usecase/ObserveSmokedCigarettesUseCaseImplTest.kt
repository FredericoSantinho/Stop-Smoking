package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.repository.ObserveSmokedCigarettesRepository
import neuro.stop.smoking.domain.test.mocks.smokedCigaretteDtoMockList
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ObserveSmokedCigarettesUseCaseImplTest {
	@Test
	fun test() = runTest {
		val observeSmokedCigarettesRepository = mock<ObserveSmokedCigarettesRepository>()

		val observeSmokedCigarettesUseCase =
			ObserveSmokedCigarettesUseCaseImpl(observeSmokedCigarettesRepository)

		val expectedSmokedCigaretteDtoList = smokedCigaretteDtoMockList()

		whenever(observeSmokedCigarettesRepository.observeSmokedCigarettes()).thenReturn(flow {
			emit(
				expectedSmokedCigaretteDtoList
			)
		})

		val firstSmokedCigarette = observeSmokedCigarettesUseCase.observeSmokedCigarettes().first()

		assertEquals(firstSmokedCigarette, expectedSmokedCigaretteDtoList)
	}
}