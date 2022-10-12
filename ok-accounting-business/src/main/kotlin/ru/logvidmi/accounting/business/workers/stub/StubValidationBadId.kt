package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING && stubCase == AccStubs.BAD_ID }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value ${contactRequest.id} must contain only"
            )
        )
    }
}