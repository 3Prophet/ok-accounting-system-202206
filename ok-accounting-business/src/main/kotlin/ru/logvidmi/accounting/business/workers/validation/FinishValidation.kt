package ru.logvidmi.accounting.business.workers.validation

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccState
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.worker

fun ICorChainDsl<AccContactContext>.finishContactRequestValidation(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING }
    handle { contactRequestValidated = contactRequestValidating }
}

fun ICorChainDsl<AccContactContext>.finishContactFilterValidation(title: String) = worker {
    this.title = title
    on { state == AccState.RUNNING }
    handle { contactFilterRequestValidated = contactFilterRequestValidating }
}