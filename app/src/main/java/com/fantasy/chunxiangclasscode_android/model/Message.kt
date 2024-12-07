package com.fantasy.chunxiangclasscode_android.model


enum class MessageType {
    text, trans, time
    ;

    val title
        get() = when (this) {
            text -> "文本消息"
            trans -> "转账消息"
            time -> "时间消息"
        }
}

// 类就是一个模版，描述了某类事物应用具有的特征(属性)和行为(方法)。
data class Message(
    val text: String,
    val isMine: Boolean = false,
    val amount: String = "",
    val type: MessageType = MessageType.text,
)