package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.models.AccWorkMode
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.chain

fun ICorChainDsl<AccContactContext>.stubs(
    title: String,
    dsl: ICorChainDsl<AccContactContext>.() -> Unit
) = chain {
    on { this.workMode == AccWorkMode.STUB && this.state == AccState.RUNNING }
    this.title = title
    dsl()
}

