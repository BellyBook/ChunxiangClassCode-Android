package com.fantasy.chunxiangclasscode_android

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AlipayDemo(
    onDismiss: () -> Unit,
): Unit {
    var price by remember {
        mutableStateOf("-1,000")
    }
    var name by remember {
        mutableStateOf("纯想老师")
    }

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
        ) {
            EditSheetContent(
                name = name,
                price = price,
                onNameChange = { name = it },
                onPriceChange = { price = it }
            )
            Text("Sheet Content")
        }
    }


    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxSize()
    ) {
        Navbar(onDismiss = onDismiss) {
            showSheet = true
        }

        // 交易明细
        Card1(
            name = name,
            price = price,
        )
        // 账单管理
        Card2()
    }
}

@Preview
@Composable
fun EditSheetContentPreview() {
    EditSheetContent(name = "纯想老师", price = "100", onNameChange = {}, onPriceChange = {})
}

@Composable
fun EditSheetContent(
    name: String,
    price: String,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .padding(bottom = 200.dp)
            .fillMaxWidth(),
    ) {
        Text(text = "请输入姓名", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = name,
            onValueChange = onNameChange,
            textStyle = TextStyle(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "请输入金额", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = price,
            onValueChange = onPriceChange,
            textStyle = TextStyle(fontSize = 20.sp)
        )
    }
}

@Composable
private fun Navbar(
    onDismiss: () -> Unit,
    onClickRight: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 14.dp)
            .padding(top = 50.dp)
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
                    .clickable { onDismiss() }
                    .offset(x = (-12).dp)
                    .rotate(90f)
                    .size(44.dp)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "全部账单",
                fontSize = 16.sp,
                color = Color(color = 0xFF333333),
                modifier = Modifier.clickable {
                    onClickRight()
                }
            )
        }

        Text(
            text = "账单详情",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Card2() {
    // 账单管理
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "账单管理",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "账单分类",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "生活服务",
                color = Color(color = 0xFF999999),
                fontSize = 16.sp,
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = null,
                tint = Color(color = 0xFF999999).copy(alpha = 0.5f),
                modifier = Modifier.rotate(-90f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "标签和备注",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "添加",
                color = Color(color = 0xFF999999),
                fontSize = 16.sp,
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = null,
                tint = Color(color = 0xFF999999).copy(alpha = 0.5f),
                modifier = Modifier.rotate(-90f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "计入收支",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = true, onCheckedChange = {},
                modifier = Modifier.scale(0.8f),
            )
        }
        HorizontalDivider(color = Color(0xFFF5F5F5))
        Row {
            BlueRow(
                icon = R.drawable.phone,
                text = "联系收款方"
            )
            BlueRow(
                icon = R.drawable.search,
                text = "查看往来记录"
            )
        }
        Row {
            BlueRow(
                icon = R.drawable.aa,
                text = "AA收款"
            )
            BlueRow(
                icon = R.drawable.tag,
                text = "往来流水证明"
            )
        }
        Row {
            BlueRow(
                icon = R.drawable.message,
                text = "申请电子回单"
            )
            BlueRow(
                icon = R.drawable.help,
                text = "对此订单有疑问"
            )
        }
    }
}

@Composable
private fun RowScope.BlueRow(
    icon: Int,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.weight(1f)
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xff0B3C7F),
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card1(
    name: String,
    price: String = "-1,100.00",
) {
    var showPicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    val timeState = rememberTimePickerState(is24Hour = true)
    if (showPicker) {
        ModalBottomSheet(
            onDismissRequest = { showPicker = false },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePicker(state = dateState)
                TimePicker(
                    state = timeState
                )
            }

        }
    }
    // 交易明细
    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .padding(top = 22.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(top = 32.dp)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(text = name)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = price,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(text = "交易成功")
            }

            val payTime = "支付时间"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = payTime,
                    fontSize = 16.sp,
                    color = Color(color = 0xFF999999),
                    modifier = Modifier.width(114.dp),
                )
                val date = formatTimestamp(dateState.selectedDateMillis!!)
                Text(
                    text = "$date ${"%02d:%02d".format(timeState.hour, timeState.minute)}:03",
                    modifier = Modifier.clickable {
                        showPicker = true
                    })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "付款方式",
                    fontSize = 16.sp,
                    color = Color(color = 0xFF999999),
                    modifier = Modifier.width(114.dp),
                )
                Text(text = "余额宝")
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = null,
                    tint = Color(color = 0xFF999999).copy(alpha = 0.5f),
                    modifier = Modifier.rotate(-90f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "商品说明",
                    fontSize = 16.sp,
                    color = Color(color = 0xFF999999),
                    modifier = Modifier.width(114.dp),

                    )
                Text(text = "收钱码收款")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "收款方全称",
                    fontSize = 16.sp,
                    color = Color(color = 0xFF999999),
                    modifier = Modifier.width(114.dp),
                )
                Text(text = "**良（个人）")
            }


            var isShowMore by remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = isShowMore
            ) {
                Box(modifier = Modifier
                    .background(Color.Red, CircleShape)
                    .size(100.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isShowMore = !isShowMore
                }
            ) {
                Text(
                    text = if (isShowMore)  "收起" else  "更多",
                    fontSize = 16.sp,
                    color = Color(color = 0xFF999999)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = null,
                    tint = Color(color = 0xFF999999).copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp).rotate(if (isShowMore) 180f else 0f)
                )



            }

        }

        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.shop),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(44.dp)
        )
    }

}

fun formatTimestamp(selectedDateMillis: Long): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(Date(selectedDateMillis))
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun GreetingPreview() {
    AlipayDemo(onDismiss = {})
}