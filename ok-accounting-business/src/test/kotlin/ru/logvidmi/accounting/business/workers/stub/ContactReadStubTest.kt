package ru.logvidmi.accounting.business.workers.stub

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactReadStubTest {

    private val processor = AccContactProcessor()
    private val id = AccContactId("1");

    @Test
    fun `read contact success`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.READ,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.SUCCESS,
            contactRequest = AccContact(id = id)
        )
        processor.exec(ctx)
        assertEquals(AccState.FINISHING, ctx.state)
        assertEquals(ctx.contactRequest.id, ctx.contactResponse.id)
        assertNotNull(ctx.contactResponse.name)
    }

    @Test
    fun `bad id`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.READ,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.BAD_ID,
            contactRequest = AccContact(
                id = id,
            )
        )
        processor.exec(ctx)
        assertEquals(AccState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $id must contain only"
            ), ctx.errors.firstOrNull()
        )
    }

    @Test
    fun `db error`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.READ,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.DB_ERROR,
            contactRequest = AccContact(
                id = id,
            )
        )
        processor.exec(ctx)
        assertEquals(AccState.FAILING, ctx.state)
        assertEquals(
            AccError(code = "internal-db", group = "internal", message = "Db error"),
            ctx.errors.firstOrNull()
        )

    }

}