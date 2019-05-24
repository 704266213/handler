package com.handler.test

import com.handler.core.Handler
import com.handler.core.Looper
import com.handler.core.Message
import java.util.UUID


class Test {

    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
//            Test().testMain()
            Test().testThread()
        }
    }

    fun testThread() {
        Thread(Runnable {
            Looper.prepare()
            val handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    println("当前线程${Thread.currentThread().name}   接受到的消息:${msg.any}")
                }
            }

            for (i in 0..9) {
                Thread(Runnable {
                    val message = Message()
                    message.what = 1
                    synchronized(UUID::class.java) {
                        message.any = UUID.randomUUID().toString()
                    }
                    handler.sendMessage(message)
                    println("当前线程:${Thread.currentThread().name}   发送的消息:${message.any}")
                }).start()
            }

            Looper.looper()
        }).start()
    }

    fun testMain() {
        Looper.prepare()
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                println("当前线程${Thread.currentThread().name}   接受到的消息:${msg.any}")
            }
        }
        for (i in 0..9) {
            Thread(Runnable {
                val message = Message()
                message.what = 1
                synchronized(UUID::class.java) {
                    message.any = UUID.randomUUID().toString()
                }
                handler.sendMessage(message)
                println("当前线程:${Thread.currentThread().name}   发送的消息:${message.any}")
            }).start()
        }
        Looper.looper()
    }


}