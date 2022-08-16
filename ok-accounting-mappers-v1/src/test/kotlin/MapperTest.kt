package ru.otus.logvidmi.accounting.mappers.v1

import AccContext
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.logvidmi.accounting.api.v1.models.*

class MapperTest {

    @Test
    fun fromTransport() {
        val req = ContactCreateRequest(
            requestId = "1234",
            debug = ContactDebug(
                mode = ContactRequestDebugMode.STUB,
                stub = ContactRequestDebugStubs.SUCCESS
            ),
            contact = ContactCreateObject(
                name = "name"
            )
        )

        val context = AccContext()
        context.fromTransport(req)

        assertEquals(AccStubs.SUCCESS, context.stubCase)
        assertEquals(AccWorkMode.STUB, context.workMode)
        assertEquals("name", context.contactRequest.name)
    }

    @Test
    fun toTransport() {
        val context = AccContext(
            requestId = AccRequestId("1234"),
            command = AccCommand.CREATE,
            contactResponse = AccContact(
                name = "name"
            ),
            errors = mutableListOf(
                AccError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = AccState.RUNNING
        )

        val resp = context.toTransportContact() as ContactCreateResponse

        assertEquals("1234", resp.requestId)
        assertEquals("name", resp.contact?.name)
        assertEquals(1, resp.errors?.size)

        val error = resp.errors?.firstOrNull()

        assertEquals("err", error?.code)
        assertEquals("request", error?.group)
        assertEquals("title", error?.field)
        assertEquals("wrong title", error?.message)
    }
}