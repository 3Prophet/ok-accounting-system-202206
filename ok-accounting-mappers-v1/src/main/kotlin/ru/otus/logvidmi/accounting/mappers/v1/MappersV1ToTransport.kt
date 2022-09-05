package ru.otus.logvidmi.accounting.mappers.v1

import AccContext
import ok.logvidmi.accounting.common.models.*
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.exceptions.UnknownAccCommand

fun AccContext.toTransportContact(): IResponse = when(val cmd = command) {
    AccCommand.CREATE -> toTransportCreate()
    AccCommand.READ -> toTransportRead()
    AccCommand.UPDATE -> toTransportUpdate()
    AccCommand.DELETE -> toTransportDelete()
    AccCommand.SEARCH -> toTransportSearch()
    AccCommand.NONE -> throw UnknownAccCommand(cmd)
}

fun AccContext.toTransportCreate() = ContactCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContext.toTransportRead() = ContactReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContext.toTransportUpdate() = ContactUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContext.toTransportDelete() = ContactDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContext.toTransportSearch() = ContactSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contacts = contactsResponse.toTransportContact()
)

private fun List<AccError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun AccError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun AccContact.toTransportContact(): ContactResponseObject = ContactResponseObject(
    id = id.takeIf { it != AccContactId.NONE}?.asString(),
    name = name.takeIf { it.isNotBlank() }
)

private fun List<AccContact>.toTransportContact(): List<ContactResponseObject>? = this
    .map {it.toTransportContact()}
    .toList()
    .takeIf { it.isNotEmpty() }