package ru.logvidmi.accounting.business

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.deepCopy
import ok.logvidmi.accounting.common.models.AccCommand
import ok.logvidmi.accounting.common.models.AccContactId
import ru.logvidmi.accounting.business.general.operation
import ru.logvidmi.accounting.business.workers.*
import ru.logvidmi.accounting.business.workers.validation.*
import ru.otus.logvidmi.accounting.cor.rootChain
import ru.otus.logvidmi.accounting.cor.worker

class AccContactProcessor {

    suspend fun exec(ctx: AccContactContext) = BusinessChain.exec(ctx)

    companion object {
       private val BusinessChain =  rootChain<AccContactContext>() {
            initStatus("Hello")

           operation("Create Contact", AccCommand.CREATE) {
               stubs("Processing stubs") {
                   stubCreateContactSuccess("Successful contact creation imitation")
                   stubValidationBadName("Bad name validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
               validation {
                   worker("Copying request into contactRequestValidating") { contactRequestValidating = contactRequest.deepCopy() }
                   worker("Trimming company name") { contactRequestValidating.name = contactRequestValidating.name.trim() }
                   validateNameIsNotEmpty("Validating company name is not empty")
                   finishContactRequestValidation("Complete contact request validation")
               }
           }

           operation("Read Contact", AccCommand.READ) {
               stubs("Processing stubs") {
                   stubReadContactSuccess("Successful contact read imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
               validation {
                   worker("Copying request into contactRequestValidating") { contactRequestValidating = contactRequest.deepCopy() }
                   worker("Trimming company id") { contactRequestValidating.id = AccContactId(contactRequestValidating.id.asString().trim()) }
                   validateContactIdIsNotEmpty("Validating company id is not empty")
                   validateContactIdFormat("Validating company id has correct format")
                   finishContactRequestValidation("Complete contact request validation")
               }

           }

           operation("Update Contact", AccCommand.UPDATE) {
               stubs("Processing stubs") {
                   stubUpdateContactSuccess("Successful contact update imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubValidationBadName("Bad name validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
               validation {
                   worker("Copying request into contactRequestValidating") { contactRequestValidating = contactRequest.deepCopy() }
                   worker("Trimming company name") { contactRequestValidating.name = contactRequestValidating.name.trim() }
                   worker("Trimming company id") { contactRequestValidating.id = AccContactId(contactRequestValidating.id.asString().trim()) }
                   validateContactIdIsNotEmpty("Validating company id is not empty")
                   validateContactIdFormat("Validating company id has correct format")
                   validateNameIsNotEmpty("Validating company name is not empty")
                   finishContactRequestValidation("Complete contact request validation")
               }
           }

           operation("Delete Contact", AccCommand.DELETE) {
               stubs("Processing stubs") {
                   stubDeleteContactSuccess("Successful contact delete imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
               validation {
                   worker("Copying request into contactRequestValidating") { contactRequestValidating = contactRequest.deepCopy() }
                   worker("Trimming company id") { contactRequestValidating.id = AccContactId(contactRequestValidating.id.asString().trim()) }
                   validateContactIdIsNotEmpty("Validating company id is not empty")
                   validateContactIdFormat("Validating company id has correct format")
                   finishContactRequestValidation("Complete contact request validation")
               }
           }

           operation("Search Contacts", AccCommand.SEARCH) {
               stubs("Processing stubs") {
                   stubSearchContactsSuccess("Successful contacts search imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
               validation {
                   worker("Copying contactFilterRequest into contactFilterRequestValidating") {contactFilterRequestValidating = contactFilterRequest.deepCopy() }
                   worker("Trimming search string") { contactFilterRequestValidating.searchString = contactFilterRequestValidating.searchString.trim() }
                   validateSearchStringIsNotEmpty("Validating search string is not empty")
                   finishContactFilterValidation("Complete search string validation")
               }
           }

       }.build()
    }
}