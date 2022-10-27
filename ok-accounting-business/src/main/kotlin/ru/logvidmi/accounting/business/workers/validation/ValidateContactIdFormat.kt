package ru.logvidmi.accounting.business.workers.validation

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccContactId
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.validateContactIdFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { contactRequestValidating.id != AccContactId.NONE && ! contactRequestValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = contactRequestValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}