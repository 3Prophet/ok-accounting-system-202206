package ru.logvidmi.accounting.business.workers.validation

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccError
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.validateNameIsNotEmpty(title: String) = worker {
    this.title = title
    on { this.contactRequestValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
        ))
    }
}