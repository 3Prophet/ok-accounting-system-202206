package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccContactId
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubSearchContactsSuccess(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING && stubCase == AccStubs.SUCCESS }
    handle {
        state = AccState.FINISHING
        contactsResponse.addAll(
            arrayOf("1", "2").map {
                AccContact(
                    id = AccContactId(it), name = "${contactFilterRequest.searchString}${it}"
                )
            }
        )
    }
}