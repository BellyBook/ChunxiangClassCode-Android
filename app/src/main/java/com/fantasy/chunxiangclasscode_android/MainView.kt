package com.fantasy.chunxiangclasscode_android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

enum class TabBarType {
    模拟器, 系统ui,
    ;
}

class MainViewModel : ViewModel() {
    var tabBarType by mutableStateOf(TabBarType.模拟器)
}

class MainView: Screen {
    @Composable
    override fun Content() {
        val vm: MainViewModel = viewModel()

        Scaffold(
            bottomBar = {
                TabBar()
            }
        ) {
            val navigator = LocalNavigator.current
            Box(Modifier.padding(it)) {
                when (vm.tabBarType) {
                    TabBarType.模拟器 -> Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            navigator?.push(AlipayDemo())
                        }) {
                            Text("Alipay Demo")
                        }

                        Button(onClick = {
                            navigator?.push(WechatDemo())
                        }) {
                            Text("WeChat Demo")
                        }
                    }
                    TabBarType.系统ui -> SystemUI()
                }
            }
        }
    }

}

@Composable
fun TabBar(
    vm: MainViewModel = viewModel()
) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .padding(horizontal = 16.dp)
    ) {
        TabBarType.entries.forEach { type ->
            val select = type == vm.tabBarType

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable {
                        vm.tabBarType = type
                    }
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                if (select) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .border(1.dp, Color.Red, RoundedCornerShape(10.dp))
                            .matchParentSize()
                    )
                }
                Text(
                    text = type.name,
                    color = if (select) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView().Content()
}