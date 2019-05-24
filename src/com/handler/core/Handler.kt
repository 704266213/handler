package com.handler.core

open class Handler {


    /**
     *  获取当前线程的Looper对象
     */
    private val looper = Looper.mylopper()

    fun sendMessage(message: Message) {
        //将handler存入到Message
        message.target = this
        //将消息放入到队列
        looper.messageQueue.enqueueMessage(message)
    }

    fun dispatchMessage(message: Message) {
        handleMessage(message)
    }


    open fun handleMessage(message: Message) {

    }


}