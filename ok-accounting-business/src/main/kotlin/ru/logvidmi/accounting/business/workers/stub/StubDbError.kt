package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubDbError(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING && stubCase == AccStubs.DB_ERROR }
    handle {
        fail(
            AccError(code = "internal-db", group = "internal", message = "Db error")
        )
    }
}