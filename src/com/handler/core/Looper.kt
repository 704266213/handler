package com.handler.core


class Looper {

    val messageQueue: MessageQueue = MessageQueue()

    companion object {

        /**
         *  threadLocal保证一个线程对应一个Looper对应一个MessageQueue
         */
        private val threadLocal: ThreadLocal<Looper> = ThreadLocal()

        /**
         *  获取当前线程对应的Looper
         */
        fun mylopper() = threadLocal.get()!!

        fun prepare() {
            //判断当前线程是否已经存在Looper对应
            if (threadLocal.get() != null) {
                throw  RuntimeException("Only one Looper may be created per thread")
            }
            //将Looper存入当前的线程里面
            threadLocal.set(Looper())
        }

        fun looper() {
            //获取当前线程的Looper
            val looper = mylopper()
            //获取当前线程的Looper对应的messageQueue
            val messageQueue = looper.messageQueue
            while (true) {
                //从messageQueue读取消息
                val message = messageQueue.next()
                //将读取到消息交给Handler处理
                message.target.dispatchMessage(message)
            }
        }
    }


}
