package ru.logvidmi.accounting.business

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        Assertions.assertEquals(AccState.FINISHING, ctx.state)
        Assertions.assertEquals(ctx.contactRequest.id, ctx.contactResponse.id)
        Assertions.assertNotNull(ctx.contactResponse.name)
    }
}