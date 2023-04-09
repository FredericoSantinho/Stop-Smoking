package neuro.stop.smoking.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.test.mocks.roomStartOfDayMock
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

class ObserveStartOfDayRepositoryImplTest {

	@Test
	fun test() = runTest {
		val startOfDayDao = mock<StartOfDayDao>()

		val observeStartOfDayRepository = ObserveStartOfDayRepositoryImpl(startOfDayDao)

		whenever(startOfDayDao.observeStartOfDay()).thenReturn(flow { emit(roomStartOfDayMock()) })

		verifyNoInteractions(startOfDayDao)

		val startOfDayDto = observeStartOfDayRepository.observeStartOfDay().first()
		val expectedStartOfDayDto = startOfDayDtoMock()

		assertEquals(expectedStartOfDayDto, startOfDayDto)
	}
}