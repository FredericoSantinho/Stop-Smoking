package neuro.stop.smoking.data.repository

import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.test.mocks.roomStartOfDayMock
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class SetStartOfDayRepositoryImplTest {

	@Test
	fun test() = runTest {
		val startOfDayDao = mock<StartOfDayDao>()

		val setStartOfDayRepository = SetStartOfDayRepositoryImpl(startOfDayDao)

		val startOfDayDto = startOfDayDtoMock()
		val roomStartOfDay = roomStartOfDayMock()

		verifyNoInteractions(startOfDayDao)

		setStartOfDayRepository.setStartOfDay(startOfDayDto)

		verify(startOfDayDao, times(1)).insert(roomStartOfDay)
	}
}