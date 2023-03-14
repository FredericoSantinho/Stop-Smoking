package neuro.test.rx

import java.util.concurrent.atomic.AtomicLong

val incrementer = Incrementer()

class Incrementer {
	private val counter = AtomicLong(0)

	fun getAndIncrement(): Long {
		return counter.getAndIncrement()
	}

	fun getAll(): List<Long> {
		val list = mutableListOf<Long>()
		for (i in 0..counter.get() - 1) {
			list.add(i)
		}
		return list
	}
}