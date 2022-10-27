package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccState
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == AccState.NONE }
    handle { state = AccState.RUNNING }
}