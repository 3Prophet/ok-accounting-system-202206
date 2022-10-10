package ru.logvidmi.accounting.business.workers.stub

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactUpdateStubTest {

    private val processor = AccContactProcessor()
    private val id = AccContactId("1");

    @Test
    fun `update contact success`() = runTest {
        val ctx = AccContactContext(
            command = AccCommand.UPDATE,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.SUCCESS,
            contactRequest = AccContact(id = id, name = "name1")
        )

        val newName = "name1"

        processor.exec(ctx)
        Assertions.assertEquals(AccState.FINISHING, ctx.state)
        Assertions.assertEquals(ctx.contactRequest.id, ctx.contactResponse.id)
        Assertions.assertEquals(newName, ctx.contactResponse.name)
    }
}