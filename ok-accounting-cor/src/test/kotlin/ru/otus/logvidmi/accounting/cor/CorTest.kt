package ru.otus.logvidmi.accounting.cor

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.logvidmi.accounting.cor.handlers.CorChain
import ru.otus.logvidmi.accounting.cor.handlers.CorWorker
import ru.otus.logvidmi.accounting.cor.handlers.executeSequential

class CorTest {

    @Test
    fun `worker should execute handle`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { history += "w1; " }
        )
        val context = TestContext()
        worker.exec(context)
        assertEquals("w1; ", context.history)
    }

    @Test
    fun `worker should not execute when off`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockOn = { status == CorStatuses.ERROR },
            blockHandle = { history += "w1; " }
        )
        val context = TestContext()
        worker.exec(context)
        assertEquals("", context.history)
    }

    @Test
    fun `worker should handle exception`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { throw RuntimeException("Custom Error") },
            blockExcept = { history += it.message }
        )
        val context = TestContext()
        worker.exec(context)
        assertEquals("Custom Error", context.history)
    }

    @Test
    fun `chain should execute workers`() = runBlocking {
        val createWorker = { title: String ->
            CorWorker<TestContext>(
                title = title,
                blockOn = { status == CorStatuses.NONE },
                blockHandle = { history += "${title}; " }
            )
        }

        val chain = CorChain<TestContext>(
            title = "chain",
            execs = listOf(createWorker("w1"), createWorker("w2")),
            handler = ::executeSequential,
        )
        val context = TestContext()
        chain.exec(context)

        assertEquals("w1; w2; ", context.history)
    }

    private fun execute(dsl: ICorExecDsl<TestContext>): TestContext = runBlocking {
        val context = TestContext()
        dsl.build().exec(context)
        context
    }

    @Test
    fun `handle should execute`() {
        assertEquals(
            "w1", execute(
                rootChain {
                    worker {
                        handle { history = "w1" }
                    }
                }).history
        )
    }
}

data class TestContext(
    var status: CorStatuses = CorStatuses.NONE,
    var sum: Int = 0,
    var history: String = "",
)

enum class CorStatuses {
    NONE,
    RUNNING,
    FAILING,
    DONE,
    ERROR
}