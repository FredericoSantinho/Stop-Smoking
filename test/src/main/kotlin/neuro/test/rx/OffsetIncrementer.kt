package neuro.test.rx

class OffsetIncrementer(
	private val incrementer: Incrementer = Incrementer(),
	private val interval: Int = 1000
) {
	fun getAndIncrement(): Long {
		return incrementer.getAndIncrement() * interval
	}
}