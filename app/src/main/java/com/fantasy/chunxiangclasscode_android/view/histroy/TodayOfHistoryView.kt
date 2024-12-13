package com.fantasy.chunxiangclasscode_android.view.histroy


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.fantasy.chunxiangclasscode_android.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


// 顶层响应
data class HistoryResponse(
    val reason: String,
    val result: List<HistoryEvent>,
    val error_code: Int
)

// 具体历史事件
data class HistoryEvent(
    val day: String,
    val date: String,
    val title: String,
    val e_id: String
) {
    companion object {
        fun mock() = HistoryEvent(
            day = "12月12日",
            date = "2023-12-12",
            title = "孙中山诞辰",
            e_id = "539"
        )
    }
}

class TodayOfHistoryViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var list by mutableStateOf(emptyList<HistoryEvent>())

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                isLoading = true
            }

            val client = OkHttpClient()
            val url =
                "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=e1300567e653b2e517e66f609d006e42&date=12%2F12"
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute().body?.string() ?: ""
            // 解析
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val data = moshi.adapter(HistoryResponse::class.java).fromJson(response)

            withContext(Dispatchers.Main) {
                list = data?.result ?: emptyList()
                isLoading = false
            }
        }
    }
}

class TodayOfHistoryView : Screen {
    @Composable
    override fun Content() {
        val vm: TodayOfHistoryViewModel = viewModel()
        Scaffold(
            topBar = {
                Navbar()
            }
        ) { innerPadding ->
            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    contentPadding = PaddingValues(
                        horizontal = 18.dp,
                        vertical = innerPadding.calculateTopPadding()
                    ),
                ) {
                    items(vm.list) { item ->
                        Column(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(text = item.title)
                            Text(text = item.date)
                        }

                    }
                }
            }
        }
    }

    @Composable
    fun Navbar(
        vm: TodayOfHistoryViewModel = viewModel()
    ) {
        val navigator = LocalNavigator.current
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .height(48.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 14.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = null,
                    tint = Color(color = 0xFF333333),
                    modifier = Modifier
                        .clickable { navigator?.pop() }
                        .offset(x = (-12).dp)
                        .rotate(90f)
                        .size(44.dp)
                )
                Spacer(Modifier.weight(1f))
            }

            Text(
                text = "获取数据",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        vm.getData()
                    }
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val vm: TodayOfHistoryViewModel = viewModel()
    vm.list = (1..10).map {
        HistoryEvent.mock()
    }
    TodayOfHistoryView().Content()
}