package ok.logvidmi.accounting.common.stubs

import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccContactId

object AccContactStubs {

    fun get(): AccContact = READ_CONTACT_STUB.copy()

    private val READ_CONTACT_STUB: AccContact
        get() = AccContact(
            id = AccContactId("333"),
            name = "New Contact"
        )
}