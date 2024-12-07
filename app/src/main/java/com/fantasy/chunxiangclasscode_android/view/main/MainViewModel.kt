package com.fantasy.chunxiangclasscode_android.view.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class TabBarType {
    模拟器, 系统ui,
    ;
}

class MainViewModel : ViewModel() {
    var tabBarType by mutableStateOf(TabBarType.模拟器)
}
