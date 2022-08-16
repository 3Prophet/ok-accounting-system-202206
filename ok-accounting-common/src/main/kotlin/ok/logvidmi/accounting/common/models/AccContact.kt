package ok.logvidmi.accounting.common.models


data class AccContact(
    var id: AccContactId = AccContactId.NONE,
    var name: String = "",
)