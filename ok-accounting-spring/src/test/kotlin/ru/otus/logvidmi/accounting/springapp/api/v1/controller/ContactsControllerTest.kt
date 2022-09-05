package ru.otus.logvidmi.accounting.springapp.api.v1.controller

import AccContext
import com.fasterxml.jackson.databind.ObjectMapper
import ok.logvidmi.accounting.common.stubs.AccContactStubs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.logvidmi.accounting.api.v1.models.*
import ru.otus.logvidmi.accounting.mappers.v1.*

@WebMvcTest(ContactsController::class)
internal class ContactsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `calling create contacts returns status ok`() {
        val request = mapper.writeValueAsString(ContactCreateRequest())
        val response = mapper.writeValueAsString(
            AccContext().apply { contactResponse = AccContactStubs.get() }.toTransportCreate()
        )

        mvc.perform(
            post("/api/v1/contacts/create")
            .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling read contacts returns status ok`() {
        val request = mapper.writeValueAsString(ContactReadRequest())
        val response = mapper.writeValueAsString(
            AccContext().apply { contactResponse = AccContactStubs.get() }.toTransportRead()
        )

        mvc.perform(
            post("/api/v1/contacts/read")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling update contacts returns status ok`() {
        val request = mapper.writeValueAsString(ContactUpdateRequest())
        val response = mapper.writeValueAsString(
            AccContext().apply { contactResponse = AccContactStubs.get() }.toTransportUpdate()
        )

        mvc.perform(
            post("/api/v1/contacts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling delete contacts returns status ok`() {
        val request = mapper.writeValueAsString(ContactDeleteRequest())
        val response = mapper.writeValueAsString(
            AccContext().apply { contactResponse = AccContactStubs.get() }.toTransportDelete()
        )

        mvc.perform(
            post("/api/v1/contacts/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }

    @Test
    fun `calling search contacts returns status ok`() {
        val request = mapper.writeValueAsString(ContactSearchRequest())
        val response = mapper.writeValueAsString(
            AccContext().apply { contactResponse = AccContactStubs.get() }.toTransportSearch()
        )

        mvc.perform(
            post("/api/v1/contacts/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}