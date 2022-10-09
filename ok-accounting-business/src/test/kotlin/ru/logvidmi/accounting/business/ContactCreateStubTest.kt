package ru.logvidmi.accounting.business

import ok.logvidmi.accounting.common.AccContactContext
import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
    }
}