import kotlinx.datetime.Instant
import ok.logvidmi.accounting.common.models.*
import ok.logvidmi.accounting.common.stubs.AccStubs

data class AccContext(
    var command: AccCommand =AccCommand.NONE,
    var state: AccState = AccState.NONE,
    var errors: MutableList<AccError> = mutableListOf(),

    var workMode: AccWorkMode = AccWorkMode.PROD,
    var stubCase: AccStubs = AccStubs.NONE,

    var requestId: AccRequestId = AccRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var contactRequest: AccContact = AccContact(),
    var contactFilterRequest: AccContactFilter = AccContactFilter(),
    var contactResponse: AccContact = AccContact(),
    var contactsResponse: MutableList<AccContact> = mutableListOf(),
    )