package neuro.test.rx

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertTrue

open class ObserveSubscriptionTest(private val offsetIncrementer: OffsetIncrementer = OffsetIncrementer()) {
	private val subscriptions = mutableMapOf<Long, Boolean>()

	fun <T : Any> Observable<T>.observeSubscription(
		incrementer: Incrementer = Incrementer(),
		offset: Long = 0
	): Observable<T> {
		return doOnSubscribe { setSubscribed(incrementer.getAndIncrement() + offset) }
	}

	fun <T : Any> Single<T>.observeSubscription(
		incrementer: Incrementer = Incrementer(),
		offset: Long = 0
	): Single<T> {
		return doOnSubscribe { setSubscribed(incrementer.getAndIncrement() + offset) }
	}

	fun <T : Any> Maybe<T>.observeSubscription(
		incrementer: Incrementer = Incrementer(),
		offset: Long = 0
	): Maybe<T> {
		return doOnSubscribe { setSubscribed(incrementer.getAndIncrement() + offset) }
	}

	fun Completable.observeSubscription(
		incrementer: Incrementer = Incrementer(),
		offset: Long = 0
	): Completable {
		return doOnSubscribe { setSubscribed(incrementer.getAndIncrement() + offset) }
	}

	fun assertSubscription(i: Long = 0, offset: Long = 0) {
		if (subscriptions.contains(i + offset)) {
			assertTrue(subscriptions.get(i + offset)!!)
		} else {
			throw java.lang.IllegalArgumentException("Subscription $i not found!")
		}
	}

	fun assertSubscriptions(incrementer: Incrementer, offset: Long) {
		incrementer.getAll().forEach { assertSubscription(it, offset) }
	}

	fun getAndIncrementOffset(): Long {
		return offsetIncrementer.getAndIncrement()
	}

	private fun setSubscribed(i: Long) {
		subscriptions.put(i, true)
	}
}