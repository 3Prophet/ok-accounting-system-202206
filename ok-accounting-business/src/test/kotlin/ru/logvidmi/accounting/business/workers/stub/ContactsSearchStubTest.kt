package ru.logvidmi.accounting.business.workers.stub

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactsSearchStubTest {

    private val processor = AccContactProcessor()

    @Test
    fun `search contacts success`() = runTest {

        val searchString = "companyName"

        val ctx = AccContactContext(
            command = AccCommand.SEARCH,
            workMode = AccWorkMode.STUB,
            stubCase = AccStubs.SUCCESS,
            contactFilterRequest = AccContactFilter(searchString = searchString )
        )

        processor.exec(ctx)
        Assertions.assertEquals(AccState.FINISHING, ctx.state)
        Assertions.assertTrue(ctx.contactsResponse.isNotEmpty())
        Assertions.assertTrue(ctx.contactsResponse.map { it.name.contains(searchString) }.reduce{ acc, next -> acc && next})
    }
}