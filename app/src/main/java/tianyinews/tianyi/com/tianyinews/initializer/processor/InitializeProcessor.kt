package tianyinews.tianyi.com.tianyinews.initializer.processor

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.math.min

class InitializeProcessor {
    private val corePoolSize = max(2, min(Runtime.getRuntime().availableProcessors() - 1, 4))

    private val service = Executors.newFixedThreadPool(corePoolSize)

    private val awaitList = mutableListOf<() -> Unit>()
    private val blockingList = mutableListOf<() -> Unit>()
    private val asyncList = mutableListOf<() -> Unit>()

    /**
     * 立刻执行的同步任务
     */
    fun invoke(function: () -> Unit) {
        function.invoke()
    }

    /**
     * 普通的同步任务（await之后，穿插运行提升效率）
     * */
    fun blocking(function: () -> Unit) {
        blockingList.add(function)
    }

    /**
     * 异步多线程任务（CountDownLatch 等待任务完成）
     * */
    fun await(function: (() -> Unit)) {
        awaitList.add(function)
    }

    /**
     * 异步多线程任务（CountDownLatch不会等待任务完成）
     * */
    fun async(function: (() -> Unit)) {
        asyncList.add(function)
    }

    fun start() {
        /**
         *  await list（异步等待结束）
         *  blocking list（同步阻塞运行）
         *  async list（异步不关心结束）
         */
        //Log.w(
        //    "InitializeProcessor", "awaitList size = ${awaitList.size}"
        //)
        //Log.w(
        //    "InitializeProcessor", "blockingList size = ${blockingList.size}"
        //)
        //Log.w(
        //    "InitializeProcessor", "asyncList size = ${asyncList.size}"
        //)
        val latch = CountDownLatch(awaitList.size)
        awaitList.forEach {
            service.submit {
                it.invoke()
                latch.countDown()
            }
        }
        blockingList.forEach {
            it.invoke()
        }
        try {
            latch.await()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        awaitList.clear()
        blockingList.clear()
        asyncList.forEach {
            service.submit { it.invoke() }
        }
        asyncList.clear()
        service.shutdown()
    }
}