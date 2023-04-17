package neuro.stop.smoking.presentation.viewmodel.common.datetime

import neuro.stop.smoking.presentation.viewmodel.common.formatter.NumberFormaterImpl
import org.junit.Assert.*
import org.junit.Test

class TimeTextMapperImplTest {
	
	 @Test
	  fun testDefaultNumberFormater() {
		 val timeTextMapper = TimeTextMapperImpl()

		 assertEquals("01h02", timeTextMapper.map(1, 2))
	  }

	 @Test
	  fun testWithNumberFormaterImpl() {
		 val timeTextMapper = TimeTextMapperImpl(NumberFormaterImpl("%01d"))

		 assertEquals("1h2", timeTextMapper.map(1, 2))
	  }
}