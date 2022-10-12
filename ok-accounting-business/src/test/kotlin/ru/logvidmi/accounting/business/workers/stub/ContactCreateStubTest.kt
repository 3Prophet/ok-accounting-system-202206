package ru.logvidmi.accounting.business.workers.stub

import ok.logvidmi.accounting.common.AccContactContext
import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.fail
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactCreateStubTest {

    private val processor = AccContactProcessor()
    private val id = AccContactId("1");
    private val name = "name"

    @Test
    fun `create success`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.CREATE,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.SUCCESS,
            contactRequest = AccContact(
                id = id,
                name = name,
            )
        )
        processor.exec(ctx)
        assertEquals(AccState.FINISHING, ctx.state)
        assertEquals(ctx.contactRequest.id, ctx.contactResponse.id)
        assertEquals(name, ctx.contactResponse.name )
    }

    @Test
    fun `bad name error`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.CREATE,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.BAD_TITLE,
            contactRequest = AccContact(
                name = name
            )
        )
        processor.exec(ctx)
        assertEquals(AccState.FAILING, ctx.state)
        assertTrue(ctx.errors.isNotEmpty())
        assertEquals(1, ctx.errors.size)
        assertEquals(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
            ), ctx.errors.firstOrNull())
    }

    @Test
    fun `db error`() =runTest {
        val ctx = AccContactContext(
            command = AccCommand.CREATE,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.DB_ERROR,
            contactRequest = AccContact(
                name = name
            )
        )

        processor.exec(ctx)
        assertEquals(AccState.FAILING, ctx.state)
        assertEquals(AccError(code = "internal-db", group = "internal", message = "Db error"),
            ctx.errors.firstOrNull())
    }

    @Test
    fun `request stub is not available`() = runTest {

        val nonExistentStubCaseName = AccStubs.NOT_FOUND

        val ctx = AccContactContext(
            command = AccCommand.CREATE,
            workMode = AccWorkMode.STUB,
            stubCase = nonExistentStubCaseName,
            contactRequest = AccContact(
                name = name
            )
        )
        processor.exec(ctx)
        assertEquals(AccState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(            AccError(
            code = "validation",
            field = "stub",
            group = "not-found",
            message = "No stub found for stub case ${nonExistentStubCaseName.name}"
        ), ctx.errors.firstOrNull())
    }
}