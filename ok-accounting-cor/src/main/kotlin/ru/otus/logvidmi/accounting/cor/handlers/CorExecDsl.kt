package ru.otus.logvidmi.accounting.cor.handlers

import ru.otus.logvidmi.accounting.cor.ICorExecDsl

abstract  class CorExecDsl<T>: ICorExecDsl<T> {
    override var title: String = ""
    override var description: String = ""

    protected var blockOn: suspend T.() -> Boolean = { true }
    protected var blockExcept: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e }

    override fun on(function: suspend T.() -> Boolean) {
        blockOn = function
    }

    override fun except(function: suspend T.(e: Throwable) -> Unit) {
        blockExcept = function
    }
}