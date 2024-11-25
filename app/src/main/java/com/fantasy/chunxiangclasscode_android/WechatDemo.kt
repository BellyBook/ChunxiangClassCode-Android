package com.fantasy.chunxiangclasscode_android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 类就是一个模版，描述了某类事物应用具有的特征(属性)和行为(方法)。
data class Message(
    val text: String,
    val isMine: Boolean,
    val isTrans: Boolean = false,
    var amount: String = "",
    var isTime: Boolean = false,
    var time: String = "",
)

@Composable
fun WechatDemo() {
    // 什么是程序？
    // 程序就是用来处理数据的，数据可以是数字，也可以是文本，也可以是图片，还可以是音频，视频等等
    // 程序就是数据结构与算法
    // 在 Kotlin 中也叫 class 类
    // 什么是类？
    // 为什么有了 class 还要搞出 data class
    // 1. 减少了样板代码。
    // 2. 保证了数据的一致性

    // data class 不是要取代普通的 class, 而是作为一个专门用于数据处理的补充工具，在特定场景下能大大提高开发效率和代码质量。
    // 使用建议：
    // 1. 当需要一个数据容器时，使用 data class
    // 2. 当需要处理业务逻辑时，使用普通的 class


    var messages by remember {
        mutableStateOf(
            listOf(
                Message(
                    text = "咱们现在周末还有坐而论道么\n之前的有录屏没能看回放么？",
                    isMine = false
                ),
                Message(text = "有的", isMine = true),
                Message(text = "好的，299是吧，直接转给你么", isMine = false),
                Message(text = "是的", isMine = true),
                Message(text = "已被接收", isMine = false, isTrans = true, amount = "399"),
                Message(text = "已收款", isMine = true, isTrans = true, amount = "399"),
                Message(text = "怎么看回放？", isMine = false),
                Message(text = "入帐后发你", isMine = true),
            )
        )
    }
    Scaffold(
        topBar = { Navbar(onDismiss = {}) },
        bottomBar = {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.bottom_bar),
                contentDescription = null,
            )
        },
        containerColor = Color(0xffEDEDED)
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {
            messages.forEach { message ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (message.isMine) {
                        Spacer(Modifier.weight(1f))
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Gray)
                                .size(40.dp)
                        )
                    }
                    // 内容
                    if (message.isTrans) {
                        TransMessageContent(message = message)
                    } else {
                        TextMessageContent(message = message)
                    }
                    if (!message.isMine) {
                        Spacer(Modifier.weight(1f))
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Gray)
                                .size(40.dp)
                        )
                    }
                }

            }


        }
    }
}

@Composable
fun TransMessageContent(message: Message) {
    Box() {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(
                    start = if (message.isMine) 0.dp else 5.dp,
                    end = if (message.isMine) 5.dp else 0.dp
                )
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xffFDE1C3))
                .width(226.dp)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp, bottom = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp),
                    tint = Color.White
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "¥${message.amount}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = message.text,
                        color = Color.White,
                        fontSize = 13.sp,
                        )
                }
            }
            HorizontalDivider(
                color = Color.White,
                thickness = 0.5.dp,
                modifier = Modifier.padding(top = 6.dp)
            )
            Text(text = "微信转账", color = Color.White, fontSize = 12.sp)
        }

        Box(
            Modifier
                .align(if (message.isMine) Alignment.TopEnd else Alignment.TopStart)
                .offset(y = 15.dp)
                .rotate(45f)
                .background(Color(0xffFDE1C3))
                .size(10.dp)
        )
    }
}

@Composable
fun TextMessageContent(message: Message) {
    Box() {
        Text(
            text = message.text,
            modifier = Modifier
                .padding(
                    start = if (message.isMine) 0.dp else 5.dp,
                    end = if (message.isMine) 5.dp else 0.dp
                )
                .clip(RoundedCornerShape(6.dp))
                .background(if (message.isMine) Color(0xff95EC69) else Color.White)
                .padding(10.dp)
        )
        Box(
            Modifier
                .align(if (message.isMine) Alignment.TopEnd else Alignment.TopStart)
                .offset(y = 15.dp)
                .rotate(45f)
                .background(if (message.isMine) Color(0xff95EC69) else Color.White)
                .size(10.dp)
        )
    }
}

@Composable
private fun Navbar(
    onDismiss: () -> Unit,
    onClickRight: () -> Unit = {},
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
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = null,
            )
        }

        Text(
            text = "纯想老师",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.LightGray.copy(0.5f))
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Preview
@Composable
fun WechatDemoPreview() {
    WechatDemo()
}