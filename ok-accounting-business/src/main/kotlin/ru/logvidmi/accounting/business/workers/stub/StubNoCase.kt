package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING }
    handle {
        fail(
            AccError(
                code = "validation",
                field = "stub",
                group = "not-found",
                message = "No stub found for stub case ${stubCase.name}"
            )
        )

    }
}