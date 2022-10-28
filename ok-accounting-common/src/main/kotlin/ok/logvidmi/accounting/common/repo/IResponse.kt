package ok.logvidmi.accounting.common.repo

import ok.logvidmi.accounting.common.models.AccError

interface IResponse<T> {
    val data: T
    val isSuccess: Boolean
    val errors: List<AccError>
}