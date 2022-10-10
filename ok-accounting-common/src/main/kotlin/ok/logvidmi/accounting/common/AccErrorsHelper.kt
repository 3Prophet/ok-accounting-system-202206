package ok.logvidmi.accounting.common

import ok.logvidmi.accounting.common.models.AccError
import ok.logvidmi.accounting.common.models.AccState

fun AccContactContext.fail(vararg error: AccError) {
    this.errors.addAll(error)
    state = AccState.FAILING
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: AccError.Levels = AccError.Levels.ERROR,
) = AccError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

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
