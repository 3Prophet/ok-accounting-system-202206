package ru.logvidmi.accounting.business.workers.validation

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.models.AccCommand
import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactCreateValidationTest {

    private val processor: AccContactProcessor = AccContactProcessor()

    @Test
    fun `contact request is copied into contact validating request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.CREATE,
            contactRequest = AccContact(name = "Netflix")
        )

        processor.exec(context)
        assertEquals(context.contactRequest, context.contactRequestValidating)
    }

    @Test
    fun `company name is trimmed`() = runTest {
        val context = AccContactContext(
            command = AccCommand.CREATE,
            contactRequest = AccContact(name = " Netflix ")
        )

        processor.exec(context)
        assertEquals("Netflix", context.contactRequestValidating.name)
    }

    @Test
    fun `name field is empty causes validation error`() = runTest {
        val context = AccContactContext(
            command = AccCommand.CREATE,
            contactRequest = AccContact(name = "")
        )

        processor.exec(context)
        assertEquals(AccState.FAILING, context.state)
        assertEquals(1, context.errors.size)
        assertEquals(
            errorValidation(
                field = "name", violationCode = "empty",
                description = "field must not be empty"
            ), context.errors.firstOrNull()
        )
    }

    @Test
    fun `after validation contact request is copied to contact validated request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.CREATE,
            contactRequest = AccContact(name = " Netflix ")
        )

        val validatedContactRequest = AccContact(name = "Netflix")

        processor.exec(context)
        assertEquals(context.contactRequestValidating, context.contactRequestValidated)
        assertEquals(validatedContactRequest, context.contactRequestValidated)
    }
}