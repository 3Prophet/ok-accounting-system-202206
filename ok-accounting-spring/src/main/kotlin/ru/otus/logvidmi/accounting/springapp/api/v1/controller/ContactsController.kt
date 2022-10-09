package ru.otus.logvidmi.accounting.springapp.api.v1.controller

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccCommand
import ok.logvidmi.accounting.common.models.AccState
import ok.logvidmi.accounting.common.stubs.AccContactStubs
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.logvidmi.accounting.business.AccContactProcessor
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.*

@RestController
@RequestMapping("api/v1/contacts")
class ContactsController(private val processor: AccContactProcessor) {

    @PostMapping("create")
    fun createContact(@RequestBody request: ContactCreateRequest): ContactCreateResponse {
        return processContactsRequest(processor = processor, command = AccCommand.CREATE, request = request)
    }

    @PostMapping("read")
    fun readContacts(@RequestBody request: ContactReadRequest): ContactReadResponse {
        return processContactsRequest(processor = processor, command = AccCommand.READ, request = request)
    }

    @PostMapping("update")
    fun updateContacts(@RequestBody request: ContactUpdateRequest): ContactUpdateResponse {
        return processContactsRequest(processor = processor, command = AccCommand.UPDATE, request = request)
    }

    @PostMapping("delete")
    fun deleteContact(@RequestBody request: ContactDeleteRequest): ContactDeleteResponse {
        return processContactsRequest(processor = processor, command = AccCommand.DELETE, request = request)
    }

    @PostMapping("search")
    fun searchContacts(@RequestBody request: ContactSearchRequest): ContactSearchResponse {
        return processContactsRequest(processor = processor, command = AccCommand.SEARCH, request = request)
    }
}