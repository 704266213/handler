package com.handler.core

import java.util.concurrent.locks.ReentrantLock


class MessageQueue {

    //存储Message数组结构
    private val messageArray = arrayOfNulls<Message>(50)

    //入队和出队的索引位置
    private var putIndex: Int = 0
    private var takeIndex: Int = 0

    //计数器
    private var count: Int = 0

    private val lock = ReentrantLock()

    //生产消息
    private val messageArrayIsEmpty = lock.newCondition()
    //消费消息
    private val messageArrayIsFull = lock.newCondition()


    /**
     * 出队
     *
     * @return
     */
    fun next(): Message {
        lock.lock()
        //如果队列中没有消息了，那么就要阻塞，停止消费
        if (count == 0) {
            messageArrayIsEmpty.await()
        }
        val message = messageArray[if (++takeIndex === 0) 0 else takeIndex]
        count--
        messageArrayIsEmpty.signal()
        lock.unlock()
        return message!!
    }


    /**
     * 入队
     *
     * @param message
     */
    fun enqueueMessage(message: Message) {
        lock.lock()
        val size = messageArray.size
        //如果队列满了,就要阻塞,停止生产
        if (count == size) {
            messageArrayIsFull.await()
        }
        messageArray[if (++putIndex === size) 0 else putIndex] = message
        count++
        //通知消费继续
        messageArrayIsEmpty.signal()
        lock.unlock()
    }

}
