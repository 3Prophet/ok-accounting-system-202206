package ru.logvidmi.accounting.business.workers.validation

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccState
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.chain

fun ICorChainDsl<AccContactContext>.validation(dsl: ICorChainDsl<AccContactContext>.() -> Unit) = chain {
    title = "Validation"
    on { state == AccState.RUNNING }
    dsl()
}