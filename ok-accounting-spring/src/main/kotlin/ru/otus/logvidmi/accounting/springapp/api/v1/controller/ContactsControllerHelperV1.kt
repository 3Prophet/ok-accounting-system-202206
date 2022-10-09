package ru.otus.logvidmi.accounting.springapp.api.v1.controller

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.asAccError
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.AccCommand
import ru.logvidmi.accounting.business.AccContactProcessor
import ru.otus.logvidmi.accounting.api.v1.models.IRequest
import ru.otus.logvidmi.accounting.api.v1.models.IResponse
import ru.otus.logvidmi.accounting.mappers.v1.fromTransport
import ru.otus.logvidmi.accounting.mappers.v1.toTransportContact

inline fun <reified T: IRequest, reified R: IResponse> processContactsRequest(
    processor: AccContactProcessor, command: AccCommand? = null, request: T): R {
    val context = AccContactContext(timeStart = Clock.System.now());
    return try {
        context.fromTransport(request);
        runBlocking { processor.exec(context) }
        context.toTransportContact() as R
    } catch (e: Throwable) {
        command?.also { context.command = it }
        context.fail(e.asAccError())
        context.toTransportContact() as R
    }
}