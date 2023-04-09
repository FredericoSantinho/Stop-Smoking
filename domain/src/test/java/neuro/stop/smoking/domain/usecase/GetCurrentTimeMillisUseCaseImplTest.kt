package neuro.stop.smoking.domain.usecase

import org.junit.Assert.assertTrue
import org.junit.Test

class GetCurrentTimeMillisUseCaseImplTest {

	@Test
	fun test() {
		val getCurrentTimeMillisUseCase = GetCurrentTimeMillisUseCaseImpl()

		val currentTimeMillis = System.currentTimeMillis()

		assertTrue(getCurrentTimeMillisUseCase.getCurrentTimeMillis() >= currentTimeMillis)
	}
}