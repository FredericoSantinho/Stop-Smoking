package neuro.stop.smoking.presentation.viewmodel.common

import androidx.compose.runtime.mutableStateOf

open class BaseUiEvent<T> {
	protected val _uiEvent = mutableStateOf<T?>(null)
	val uiEvent = _uiEvent.asState()

	fun eventConsumed() {
		_uiEvent.value = null
	}
}