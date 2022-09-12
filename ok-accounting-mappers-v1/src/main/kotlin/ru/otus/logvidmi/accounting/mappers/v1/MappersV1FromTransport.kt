package ru.otus.logvidmi.accounting.mappers.v1

import AccContext
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.exceptions.UnknownRequestClass

fun AccContext.fromTransport(request: IRequest) = when (request) {
    is ContactCreateRequest -> fromTransport(request)
    is ContactReadRequest -> fromTransport(request)
    is ContactUpdateRequest -> fromTransport(request)
    is ContactDeleteRequest -> fromTransport(request)
    is ContactSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun ContactDebug?.transportToStubCase(): AccStubs = when (this?.stub) {
    ContactRequestDebugStubs.SUCCESS -> AccStubs.SUCCESS
    ContactRequestDebugStubs.NOT_FOUND -> AccStubs.NOT_FOUND
    ContactRequestDebugStubs.BAD_ID -> AccStubs.BAD_ID
    ContactRequestDebugStubs.BAD_TITLE -> AccStubs.BAD_TITLE
    ContactRequestDebugStubs.BAD_DESCRIPTION -> AccStubs.BAD_DESCRIPTION
    ContactRequestDebugStubs.BAD_VISIBILITY -> AccStubs.BAD_VISIBILITY
    ContactRequestDebugStubs.CANNOT_DELETE -> AccStubs.CANNOT_DELETE
    ContactRequestDebugStubs.BAD_SEARCH_STRING -> AccStubs.BAD_SEARCH_STRING
    null -> AccStubs.NONE
}

private fun IRequest?.requestId() = this?.requestId?.let { AccRequestId(it) } ?: AccRequestId.NONE

private fun ContactDebug?.transportToWorkMode(): AccWorkMode = when (this?.mode) {
    ContactRequestDebugMode.PROD -> AccWorkMode.PROD
    ContactRequestDebugMode.TEST -> AccWorkMode.TEST
    ContactRequestDebugMode.STUB -> AccWorkMode.STUB
    null -> AccWorkMode.PROD
}

private fun String?.toContactId() = this?.let { AccContactId(it) } ?: AccContactId.NONE

private fun ContactCreateObject.toInternal(): AccContact = AccContact(
    name = this.name ?: ""
)

private fun ContactUpdateObject.toInternal(): AccContact = AccContact(
    id = this.id.toContactId(),
    name = this.name ?: ""
)

private fun ContactSearchFilter?.toInternal(): AccContactFilter = AccContactFilter(
    searchString = this?.searchString ?: ""
)

private fun String?.toContactWithId() = AccContact(id = this.toContactId())

fun AccContext.fromTransport(request: ContactCreateRequest) {
    command = AccCommand.CREATE
    requestId = request.requestId()
    contactRequest = request.contact?.toInternal() ?: AccContact()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AccContext.fromTransport(request: ContactUpdateRequest) {
    command = AccCommand.UPDATE
    requestId = request.requestId()
    contactRequest = request.contact?.toInternal() ?: AccContact()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AccContext.fromTransport(request: ContactReadRequest) {
    command = AccCommand.READ
    requestId = request.requestId()
    contactRequest = request.contact?.id.toContactWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AccContext.fromTransport(request: ContactDeleteRequest) {
    command = AccCommand.DELETE
    requestId = request.requestId()
    contactRequest = request.contact?.id.toContactWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AccContext.fromTransport(request: ContactSearchRequest) {
    command = AccCommand.READ
    requestId = request.requestId()
    contactFilterRequest = request.contactFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}