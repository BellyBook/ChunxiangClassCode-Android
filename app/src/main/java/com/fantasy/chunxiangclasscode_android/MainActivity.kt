package com.fantasy.chunxiangclasscode_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    fun sayHello(name: String?, age: Int = 30) {
        println("Hello, ${name ?: "纯想老师"}! You are $age years old")
    }

    fun doWithCallback(callback: () -> Unit) {
        println("开始执行任务")
        callback()
        println("任务执行完成")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainView()
        }
    }

    @Composable
    fun MainView() {
        // 0: 代表主页
        // 1: 代表支付宝
        // 2: 代表微信
        var pageIndex by remember {
            mutableIntStateOf(0)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(Color.White).fillMaxSize()
        ) {
            when (pageIndex) {
                0 -> Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Text(text = "页面一览")
                    Button(onClick = {
                        pageIndex = 1
                    }) {
                        Text(text = "支付宝")
                    }

                    Button(onClick = {
                        pageIndex = 2
                    }) {
                        Text(text = "微信")
                    }

                    Button(onClick = {
                        pageIndex = 3
                    }) {
                        Text(text = "系统 ui")
                    }
                }
                1 -> AlipayDemo(onDismiss = { pageIndex = 0 })
                2 -> WechatDemo()
                3 -> SystemUI()
            }

        }
    }


    @Preview
    @Composable
    fun MainViewPreview() {
        MainView()
    }



























    fun test() {
        // 什么叫常量 什么叫变量
        // 常量：在程序运行期间，其值不能被修改的量。
        // 变量：在程序运行期间，其值可以被修改的量。
//        val age = 10
//        var name = "纯想老师 SwiftUI"
//        name = "小梁老师 ComposeUI"

        // 由于 Kotlin 是一门类型安全的语言
        // 所以在定义变量的时候，必须指定变量的类型


//        类型	位数	范围	精度	用途
//        Int	32/64位	-2^31 ~ 2^31-1 (32位)
//        -2^63 ~ 2^63-1 (64位)	整数	计数、索引
//        Double	64位	±2.23x10^-308 ~ ±1.80x10^308	15-17位小数	高精度浮点计算
//        Float	32位	±1.18x10^-38 ~ ±3.4x10^38	6-7位小数	普通浮点计算
//        String	动态	取决于内存	UTF-8编码	文本处理
//        Bool	1位	true/false	-	逻辑判断
        var age = 10
        var weight = 55.5
        var height = 187.5f
        var s1 = "小梁老师 ComposeUI"
        var isMan = true
        // 1 - true
        // 0 - false
        age = 11
        age = 12
        age = 13
        age = 13.5.toInt()
        println("我今年 $age 岁")

        // 1. 可不可以？？
        val ageOrNull = s1.toIntOrNull()

        // 建议
        // 1. 优先使用 val （值不可变），
        // 2. 如果确实需要修改，再使用 var （值可变）
        // 3. 合理使用 null 安全
        // 4. 变量名尽可能有意义
        // 5. 变量名尽可能简洁
        // 6. 变量值可以改，但类型不能改，善用类型推导
        // 代码一定敲。多敲才能生巧

        // 变色龙
        var color = "红色"
        color = "蓝色"
        color = "绿色"

    }

    fun 条件语句() {
        // q: 什么是条件语句
        // a: 条件语句是根据条件的真假来执行不同的代码块

//        val temperature = 30
//        if (temperature > 30) {
//            println("穿短袖")
//        } else {
//            println("穿长袖")
//        }

        // 在 Kotlin 中，if 是一个表达式，可以有返回值
        val score = 60
        val state = if (score > 90) {
            "优秀"
        } else if (score > 80) {
            "良好"
        } else if (score > 60) {
            "及格"
        } else {
            "不及格"
        }
        val state2 = when {
            score > 90 -> "优秀"
            score > 80 -> "良好"
            score > 60 -> "及格"
            else -> "不及格"
        }
        println("考试成绩：$score, 考试状态：$state, $state2")

        // 什么时候用 if 什么时候用 when
        // 1. 当条件是一个值的时候，用 if
        // 2. 当条件是多个值的时候，用 when
    }
}

