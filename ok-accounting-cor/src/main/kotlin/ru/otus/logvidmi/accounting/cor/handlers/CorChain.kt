package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.CorDsl
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.ICorExecDsl

class CorChain<T>(title: String,
                  description: String = "",
                  blockOn: suspend T.() -> Boolean = { true },
                  blockExcept: suspend T.(Throwable) -> Unit = { throw it },
                  private val execs: List<ICorExec<T>>,
                  private val handler: suspend (List<ICorExec<T>>, T) -> Unit,
): AbstractCorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) = handler(execs, context)
}

suspend fun <T> executeSequential(execs: List<ICorExec<T>>, context: T) {
    execs.forEach {it.exec(context)}
}

@CorDsl
class CorChainDsl<T>(
    private val handler: suspend (List<ICorExec<T>>, T) -> Unit = ::executeSequential,
): CorExecDsl<T>(), ICorChainDsl<T> {

    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf()

    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    override fun build() = CorChain(
        title, description, blockOn, blockExcept, workers.map { it.build() }, handler)
}
