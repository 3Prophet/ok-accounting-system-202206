package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.ICorExec

class CorWorker<T>(title: String,
                   description: String = "",
                   blockOn: suspend T.() -> Boolean = { true },
                   val blockHandle: suspend T.() -> Unit,
                   blockExcept: suspend T.(Throwable) -> Unit = { throw it },
): AbstractCorExec<T>(title, description, blockOn, blockExcept) {

    override suspend fun exec(context: T) {
        if (context.blockOn()) {
            try {
                context.blockHandle()
            } catch (e: Throwable) {
                context.blockExcept(e);
            }
        }
    }

}
