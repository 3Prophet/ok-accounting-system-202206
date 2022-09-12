package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.ICorExec

class CorChain<T>(title: String,
                  description: String = "",
                  blockOn: suspend T.() -> Boolean = { true },
                  blockExcept: suspend T.(Throwable) -> Unit = { throw it },
                  val workers: List<ICorExec<T>>,
                  val handler: suspend (List<ICorExec<T>>, T) -> Unit,
): AbstractCorExec<T>(title, description, blockOn, blockExcept) {

    override suspend fun exec(context: T) {
        if (context.blockOn()) {
            try {
                handler(workers, context)
            } catch (e: Throwable) {
                context.blockExcept(e);
            }
        }
    }
}

suspend fun <T> executeSequential(execs: List<ICorExec<T>>, context: T) {
    execs.forEach {it.exec(context)}
}
