package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.CorDsl
import ru.otus.logvidmi.accounting.cor.ICorWorkerDsl

class CorWorker<T>(title: String,
                   description: String = "",
                   blockOn: suspend T.() -> Boolean = { true },
                   val blockHandle: suspend T.() -> Unit = {},
                   blockExcept: suspend T.(Throwable) -> Unit = { throw it },
): AbstractCorExec<T>(title, description, blockOn, blockExcept) {

    override suspend fun handle(context: T) = blockHandle(context)
}

@CorDsl
class CorWorkerDsl<T>: CorExecDsl<T>(), ICorWorkerDsl<T> {

    var blockHandle: suspend T.() -> Unit = {}

    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> =
        CorWorker(title, description, blockOn, blockHandle, blockExcept)
}
