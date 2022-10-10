package ru.logvidmi.accounting.business.workers.validation

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.models.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactSearchValidationTest {

    private val processor: AccContactProcessor = AccContactProcessor()

    @Test
    fun `contact filter request is copied into contact filter validating request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.SEARCH,
            contactFilterRequest = AccContactFilter(searchString = "Netflix")
        )

        processor.exec(context)
        Assertions.assertEquals(context.contactFilterRequest, context.contactFilterRequestValidating)
    }

    @Test
    fun `filter search string is trimmed`() = runTest {
        val context = AccContactContext(
            command = AccCommand.SEARCH,
            contactFilterRequest = AccContactFilter(searchString = " Netflix ")
        )

        processor.exec(context)
        Assertions.assertEquals("Netflix", context.contactFilterRequestValidating.searchString)
    }

    @Test
    fun `empty search string causes validation error`() = runTest {
        val context = AccContactContext(
            command = AccCommand.SEARCH,
            contactFilterRequest = AccContactFilter(searchString = "")
        )

        processor.exec(context)
        Assertions.assertEquals(AccState.FAILING, context.state)
        Assertions.assertEquals(1, context.errors.size)
        Assertions.assertEquals(
            errorValidation(
                field = "search string", violationCode = "empty",
                description = "field must not be empty"
            ), context.errors.firstOrNull()
        )
    }

    @Test
    fun `after validation validating filter request is copied to validated filter request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.SEARCH,
            contactFilterRequest = AccContactFilter(searchString = " Netflix ")
        )

        val validatedFilter = AccContactFilter(searchString = "Netflix")

        processor.exec(context)
        Assertions.assertEquals(context.contactFilterRequestValidating, context.contactFilterRequestValidated)
        Assertions.assertEquals(validatedFilter, context.contactFilterRequestValidated)
    }
}