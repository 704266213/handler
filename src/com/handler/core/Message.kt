package com.handler.core

class Message {
    /**
     * 处理消息返回的对象
     */
    lateinit var any: Any
    /**
     * 处理消息的状态码
     */
    var what = 0

    /**
     *
     */
    lateinit var target: Handler

}
