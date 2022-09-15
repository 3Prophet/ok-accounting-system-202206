package ru.otus.logvidmi.accounting.cor

import ru.otus.logvidmi.accounting.cor.handlers.CorChainDsl
import ru.otus.logvidmi.accounting.cor.handlers.CorWorkerDsl
import ru.otus.logvidmi.accounting.cor.handlers.ICorExec

/**
 * Common builder interface for worker and chain.
 */
@CorDsl
interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun on(function: suspend T.() -> Boolean)
    fun except(function: suspend T.(Throwable) -> Unit)

    fun build(): ICorExec<T>
}

@CorDsl
interface ICorChainDsl<T>: ICorExecDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

@CorDsl
interface ICorWorkerDsl<T>: ICorExecDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

/**
 * Usage:
 * rootChain<SomeContext> {
 *      worker {
 *      }
 *      chain {
 *          worker() {
 *          }
 *          worker() {
 *          }
 *      }
 * }
 */
fun <T> rootChain(dsl: ICorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply { dsl }

/**
 * Creates chain which elements are executed sequentially inside another chain.
 */
fun <T> ICorChainDsl<T>.chain(subChainDsl: ICorChainDsl<T>.() -> Unit) = add(CorChainDsl<T>().apply { subChainDsl })

/**
 * Creates worker within a chain.
 */
fun <T> ICorChainDsl<T>.worker(workerDsl: ICorWorkerDsl<T>.() -> Unit) = add(CorWorkerDsl<T>().apply { workerDsl })

fun <T> ICorChainDsl<T>.worker(title: String, description: String = "", blockHandle: T.() -> Unit) =
    add(CorWorkerDsl<T>().also { it.title = title; it.description = description; it.handle(blockHandle) })