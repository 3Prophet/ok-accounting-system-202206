package ru.logvidmi.accounting.business

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccCommand
import ru.logvidmi.accounting.business.general.operation
import ru.logvidmi.accounting.business.workers.*
import ru.otus.logvidmi.accounting.cor.rootChain

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
           }

           operation("Read Contact", AccCommand.READ) {
               stubs("Processing stubs") {
                   stubReadContactSuccess("Successful contact read imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
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
           }

           operation("Delete Contact", AccCommand.DELETE) {
               stubs("Processing stubs") {
                   stubDeleteContactSuccess("Successful contact delete imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
           }

           operation("Search Contacts", AccCommand.SEARCH) {
               stubs("Processing stubs") {
                   stubSearchContactsSuccess("Successful contacts search imitation")
                   stubValidationBadId("Bad id validation error imitation")
                   stubDbError("Db error imitation")
                   stubNoCase("Requested stub is not available")
               }
           }

       }.build()
    }
}