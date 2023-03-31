package neuro.stop.smoking.presentation.viewmodel.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableState<T>.asState(): State<T> =
	this

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> =
	this