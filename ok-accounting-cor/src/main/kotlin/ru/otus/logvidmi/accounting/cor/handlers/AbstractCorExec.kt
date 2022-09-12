package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.ICorExec

abstract class AbstractCorExec<T>(
    override val title: String,
    override val description: String,
    val blockOn: suspend T.() -> Boolean,
    val blockExcept: suspend T.(Throwable) -> Unit,
): ICorExec<T> {

}