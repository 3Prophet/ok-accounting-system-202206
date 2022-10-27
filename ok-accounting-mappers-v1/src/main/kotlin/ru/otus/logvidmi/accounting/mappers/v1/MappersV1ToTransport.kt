package ru.otus.logvidmi.accounting.mappers.v1

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.*
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.exceptions.UnknownAccCommand

fun AccContactContext.toTransportContact(): IResponse = when(val cmd = command) {
    AccCommand.CREATE -> toTransportCreate()
    AccCommand.READ -> toTransportRead()
    AccCommand.UPDATE -> toTransportUpdate()
    AccCommand.DELETE -> toTransportDelete()
    AccCommand.SEARCH -> toTransportSearch()
    AccCommand.NONE -> throw UnknownAccCommand(cmd)
}

fun AccContactContext.toTransportCreate() = ContactCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContactContext.toTransportRead() = ContactReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContactContext.toTransportUpdate() = ContactUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContactContext.toTransportDelete() = ContactDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    contact = contactResponse.toTransportContact()
)

fun AccContactContext.toTransportSearch() = ContactSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == AccState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
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