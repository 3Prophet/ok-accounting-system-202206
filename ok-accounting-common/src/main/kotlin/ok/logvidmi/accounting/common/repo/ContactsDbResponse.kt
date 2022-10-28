package ok.logvidmi.accounting.common.repo

import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccError

data class ContactsDbResponse(
    override val data: List<AccContact>,
    override val isSuccess: Boolean,
    override val errors: List<AccError>
) : IResponse<List<AccContact>>