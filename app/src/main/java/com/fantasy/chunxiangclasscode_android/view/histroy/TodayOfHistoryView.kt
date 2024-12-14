package com.fantasy.chunxiangclasscode_android.view.histroy


import android.widget.DatePicker
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import com.fantasy.chunxiangclasscode_android.design.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


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

    var date by mutableStateOf(LocalDate.now())

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                isLoading = true
            }

            val dateFormat = date.format(DateTimeFormatter.ofPattern("M/d"))
            val client = OkHttpClient()
            val url =
                "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=e1300567e653b2e517e66f609d006e42&date=${dateFormat}"
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

    fun setupDate(date: LocalDate) {
        this.date = date
        getData()
    }
}

class TodayOfHistoryView : Screen {
    @Composable
    override fun Content() {
        val vm: TodayOfHistoryViewModel = viewModel()
        Scaffold(
            topBar = {
                Navbar()
            },
            containerColor = Color(0xffFBF6FA)
        ) { innerPadding ->
            if (vm.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    contentPadding = PaddingValues(
                        horizontal = 18.dp,
                        vertical = innerPadding.calculateTopPadding()
                    ),
                ) {
                    items(vm.list) { item ->
                        HistoryEventRow(item)

                    }
                }
            }
        }
    }

    @Composable
    private fun HistoryEventRow(item: HistoryEvent) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.day,
                modifier = Modifier
                    .background(Color.Gray.copy(0.1f), CircleShape)
                    .padding(10.dp, 2.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                Text(text = item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        id = R.drawable.time,
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = item.date, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Navbar(
        vm: TodayOfHistoryViewModel = viewModel()
    ) {
        val navigator = LocalNavigator.current
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xffFBF6FA)
                )
                .statusBarsPadding()
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val datePickerState = rememberDatePickerState()
            var openDialog by remember { mutableStateOf(true) }
            if (openDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let {
                                val date = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(it),
                                    ZoneId.systemDefault()
                                ).toLocalDate()

                                vm.setupDate(date)
                                openDialog = false
                            }

                        }) {
                            Text(text = "确定")
                        }
                    },
                    modifier = Modifier.scale(0.9f)
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Icon(
                id = R.drawable.arrow_down,
                tint = Color(color = 0xFF333333),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { navigator?.pop() }
                    .offset(x = (-12).dp)
                    .rotate(90f)
                    .size(44.dp)
            )

            TextButton(
                onClick = {
                    openDialog = true
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = vm.date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")))
            }

            GetDataButton(
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                vm.getData()
            }

        }
    }

    @Composable
    fun GetDataButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        TextButton(onClick = onClick, modifier = modifier) {
            Text(text = "获取数据")
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