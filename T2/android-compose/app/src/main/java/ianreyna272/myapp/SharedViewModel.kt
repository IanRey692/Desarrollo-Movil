package ianreyna272.myapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {
    private val _sharedText = MutableStateFlow("Texto inicial vacío")
    val sharedText: StateFlow<String> = _sharedText.asStateFlow()

    fun updateSharedText(newText: String) {
        _sharedText.value = newText
    }
}