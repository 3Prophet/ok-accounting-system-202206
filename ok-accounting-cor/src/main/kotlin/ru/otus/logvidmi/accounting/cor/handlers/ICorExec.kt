package ru.otus.logvidmi.accounting.cor.handlers

/**
 * Basic interface of a handler in a CoR.
 */
interface ICorExec<T> {
    val title: String
    val description: String

    suspend fun exec(context: T)
}