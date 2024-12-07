package com.fantasy.chunxiangclasscode_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val toastManager = ToastManager.shared

class ToastManager private constructor() : ViewModel() {
    // 只有一个实例 单例模式
    companion object {
        val shared = ToastManager()
    }

    var toastMessage: String? by mutableStateOf(null)
        private set

    fun showToast(message: String) {
        toastMessage = message
        viewModelScope.launch {
            delay(2000)
            toastMessage = null
        }
    }
}