package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubValidationBadName(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING && stubCase == AccStubs.BAD_TITLE }
    handle {
        fail(
            AccError(code = "400", field = "name", group = "validation", message = "Illegal name")
        )
    }
}