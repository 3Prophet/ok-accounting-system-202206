package ru.logvidmi.accounting.business.workers.validation

import kotlinx.coroutines.test.runTest
import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.errorValidation
import ok.logvidmi.accounting.common.models.AccCommand
import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccContactId
import ok.logvidmi.accounting.common.models.AccState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.logvidmi.accounting.business.AccContactProcessor

class ContactDeleteValidationTest {

    private val processor: AccContactProcessor = AccContactProcessor()

    @Test
    fun `contact request is copied into contact validating request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.DELETE,
            contactRequest = AccContact(id = AccContactId("1"))
        )

        processor.exec(context)
        Assertions.assertEquals(context.contactRequest, context.contactRequestValidating)
    }

    @Test
    fun `company id is trimmed`() = runTest {
        val context = AccContactContext(
            command = AccCommand.DELETE,
            contactRequest = AccContact(id = AccContactId(" 1 "))
        )

        processor.exec(context)
        Assertions.assertEquals("1", context.contactRequestValidating.id.asString())
    }

    @Test
    fun `empty company id causes validation error`() = runTest {
        val context = AccContactContext(
            command = AccCommand.DELETE,
            contactRequest = AccContact(id = AccContactId(""))
        )

        processor.exec(context)
        Assertions.assertEquals(AccState.FAILING, context.state)
        Assertions.assertEquals(1, context.errors.size)
        Assertions.assertEquals(
            errorValidation(
                field = "id", violationCode = "empty",
                description = "field must not be empty"
            ), context.errors.firstOrNull()
        )
    }

    @Test
    fun `wrong company id format causes validation error`() = runTest {
        val wrongId = "#"
        val context = AccContactContext(
            command = AccCommand.DELETE,
            contactRequest = AccContact(id = AccContactId(wrongId))
        )

        processor.exec(context)
        Assertions.assertEquals(AccState.FAILING, context.state)
        Assertions.assertEquals(1, context.errors.size)
        Assertions.assertEquals(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $wrongId must contain only"
            ), context.errors.firstOrNull()
        )
    }

    @Test
    fun `after validation contact request is copied to contact validated request`() = runTest {
        val context = AccContactContext(
            command = AccCommand.DELETE,
            contactRequest = AccContact(id = AccContactId(" 1 "))
        )

        val validatedContactRequest = AccContact(id = AccContactId("1"))

        processor.exec(context)
        Assertions.assertEquals(context.contactRequestValidating, context.contactRequestValidated)
        Assertions.assertEquals(validatedContactRequest, context.contactRequestValidated)
    }
}