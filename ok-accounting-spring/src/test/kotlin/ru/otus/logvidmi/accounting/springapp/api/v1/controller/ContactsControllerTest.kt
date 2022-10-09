package ru.otus.logvidmi.accounting.springapp.api.v1.controller

import ok.logvidmi.accounting.common.AccContactContext
import com.fasterxml.jackson.databind.ObjectMapper
import ok.logvidmi.accounting.common.stubs.AccContactStubs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.*
import ru.otus.logvidmi.accounting.springapp.config.RequestProcessorConfig

@WebMvcTest(ContactsController::class)
@Import(RequestProcessorConfig::class)
internal class ContactsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `calling create contacts returns status ok`() {

        val requestObject = ContactCreateRequest(
            requestType = "create",
            requestId = "333",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS),
            contact = ContactCreateObject(
                name = "client"
            )
        )

        val request = mapper.writeValueAsString(requestObject)
        val response = mapper.writeValueAsString(
            AccContactContext().apply { contactResponse = AccContactStubs.get() }.toTransportCreate()
        )

        mvc.perform(
            post("/api/v1/contacts/create")
            .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling read contacts returns status ok`() {

        val requestObject = ContactReadRequest(
            requestType = "read",
            requestId = "333",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS),
            contact = ContactReadObject(
                id = "1"
            )
        )

        val request = mapper.writeValueAsString(requestObject)
        val response = mapper.writeValueAsString(
            AccContactContext().apply { contactResponse = AccContactStubs.get() }.toTransportRead()
        )

        mvc.perform(
            post("/api/v1/contacts/read")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling update contacts returns status ok`() {

        val requestObject = ContactUpdateRequest(
            requestType = "read",
            requestId = "333",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS),
            contact = ContactUpdateObject(
                id = "1",
                name = "client2",
                lock = "1"
            )
        )

        val request = mapper.writeValueAsString(requestObject)
        val response = mapper.writeValueAsString(
            AccContactContext().apply { contactResponse = AccContactStubs.get() }.toTransportUpdate()
        )

        mvc.perform(
            post("/api/v1/contacts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling delete contacts returns status ok`() {

        val requestObject = ContactDeleteRequest(
            requestType = "read",
            requestId = "333",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS),
            contact = ContactDeleteObject(
                id = "1",
                lock = "1"
            )
        )

        val request = mapper.writeValueAsString(requestObject)
        val response = mapper.writeValueAsString(
            AccContactContext().apply { contactResponse = AccContactStubs.get() }.toTransportDelete()
        )

        mvc.perform(
            post("/api/v1/contacts/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling search contacts returns status ok`() {

        val requestObject = ContactSearchRequest(
            requestType = "read",
            requestId = "333",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS),
            contactFilter = ContactSearchFilter(
                searchString = "client1"
            )
        )

        val request = mapper.writeValueAsString(requestObject)
        val response = mapper.writeValueAsString(
            AccContactContext().apply { contactResponse = AccContactStubs.get() }.toTransportSearch()
        )

        mvc.perform(
            post("/api/v1/contacts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}