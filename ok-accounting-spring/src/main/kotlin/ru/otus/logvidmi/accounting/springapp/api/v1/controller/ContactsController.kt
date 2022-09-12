package ru.otus.logvidmi.accounting.springapp.api.v1.controller

import AccContext
import ok.logvidmi.accounting.common.stubs.AccContactStubs
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.*

@RestController
@RequestMapping("api/v1/contacts")
class ContactsController {

    @PostMapping("create")
    fun createContact(@RequestBody request: ContactCreateRequest): ContactCreateResponse {
        val context = AccContext();
        context.fromTransport(request);
        context.contactResponse = AccContactStubs.get()
        return context.toTransportCreate();
    }

    @PostMapping("read")
    fun readContacts(@RequestBody request: ContactReadRequest): ContactReadResponse {
        val context = AccContext();
        context.fromTransport(request);
        context.contactResponse = AccContactStubs.get()
        return context.toTransportRead();
    }

    @PostMapping("update")
    fun updateContacts(@RequestBody request: ContactUpdateRequest): ContactUpdateResponse {
        val context = AccContext();
        context.fromTransport(request);
        context.contactResponse = AccContactStubs.get()
        return context.toTransportUpdate();
    }

    @PostMapping("delete")
    fun deleteContact(@RequestBody request: ContactDeleteRequest): ContactDeleteResponse {
        val context = AccContext();
        context.fromTransport(request);
        context.contactResponse = AccContactStubs.get()
        return context.toTransportDelete();
    }

    @PostMapping("search")
    fun searchContacts(@RequestBody request: ContactSearchRequest): ContactSearchResponse {
        val context = AccContext();
        context.fromTransport(request);
        context.contactResponse = AccContactStubs.get()
        return context.toTransportSearch();
    }
}