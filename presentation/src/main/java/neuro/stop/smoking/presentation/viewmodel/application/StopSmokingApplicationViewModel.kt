package neuro.stop.smoking.presentation.viewmodel.application

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.runBlocking
import neuro.stop.smoking.domain.repository.NewSmokedCigaretteIdRepository
import neuro.stop.smoking.domain.usecase.SaveSmokedCigaretteUseCase

class StopSmokingApplicationViewModel(
	private val newSmokedCigaretteIdRepository: NewSmokedCigaretteIdRepository,
	private val saveSmokedCigaretteUseCase: SaveSmokedCigaretteUseCase
) : ViewModel() {

	fun onCreate() {
		runBlocking(CoroutineExceptionHandler { coroutineContext, throwable ->
			Log.e("StopSmokingApplicationViewModel", "onCreate: ", throwable)
		}) {
			populateSmokedCigarettes()
		}
	}

	private suspend fun populateSmokedCigarettes() {
		val newSmokedCigaretteId = newSmokedCigaretteIdRepository.newSmokedCigaretteId()

		saveSmokedCigaretteUseCase.saveSmokedCigaretteUseCase(System.currentTimeMillis() - newSmokedCigaretteId * 1000 * 60 * 29)
	}
}