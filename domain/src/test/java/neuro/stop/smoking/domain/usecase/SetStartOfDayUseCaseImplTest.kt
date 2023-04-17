package neuro.stop.smoking.domain.usecase

import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.domain.repository.SetStartOfDayRepository
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.verifyNoInteractions

class SetStartOfDayUseCaseImplTest {
	@Test
	fun test() = runTest {
		val setStartOfDayRepository = mock<SetStartOfDayRepository>()

		val setStartOfDayUseCase = SetStartOfDayUseCaseImpl(setStartOfDayRepository)

		val startOfDayDto = startOfDayDtoMock()

		verifyNoInteractions(setStartOfDayRepository)

		setStartOfDayUseCase.setStartOfDay(startOfDayDto)

		verify(setStartOfDayRepository, times(1)).setStartOfDay(startOfDayDto)
	}
}