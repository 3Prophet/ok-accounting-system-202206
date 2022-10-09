package ru.logvidmi.accounting.business.workers

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.stubReadContactSuccess(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING && stubCase == AccStubs.SUCCESS }
    handle {
        state = AccState.FINISHING
        this.contactResponse = AccContact(id = contactRequest.id, name = contactRequest.name)
    }
}