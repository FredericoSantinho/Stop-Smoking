package neuro.stop.smoking.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import neuro.stop.smoking.data.dao.StartOfDayDao
import neuro.stop.smoking.data.test.mocks.roomStartOfDayMock
import neuro.stop.smoking.domain.test.mocks.startOfDayDtoMock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetStartOfDayRepositoryImplTest {

	@Test
	fun test() = runTest {
		val startOfDayDao = mock<StartOfDayDao>()

		val getStartOfDayRepository = GetStartOfDayRepositoryImpl(startOfDayDao)

		whenever(startOfDayDao.getStartOfDay()).thenReturn(roomStartOfDayMock())

		val startOfDayDto = getStartOfDayRepository.getStartOfDay()

		assertEquals(startOfDayDtoMock(), startOfDayDto)
	}

	@Test
	fun testNullStartOfDay() {
		val startOfDayDao = mock<StartOfDayDao>()

		val getStartOfDayRepository = GetStartOfDayRepositoryImpl(startOfDayDao)

		runBlocking {
			whenever(startOfDayDao.getStartOfDay()).thenReturn(null)

			val startOfDayDto = getStartOfDayRepository.getStartOfDay()

			assertNull(startOfDayDto)
		}
	}
}