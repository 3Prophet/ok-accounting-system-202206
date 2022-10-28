package ok.logvidmi.accounting.common.repo

import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccError

data class ContactDbResponse(
    override val data: AccContact,
    override val isSuccess: Boolean,
    override val errors: List<AccError>
) : IResponse<AccContact>