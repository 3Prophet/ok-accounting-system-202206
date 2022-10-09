package ok.logvidmi.accounting.common

import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState

fun AccContactContext.fail(vararg error: AccError) {
    this.errors.addAll(error)
    state = AccState.FAILING
}

fun Throwable.asAccError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = AccError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
