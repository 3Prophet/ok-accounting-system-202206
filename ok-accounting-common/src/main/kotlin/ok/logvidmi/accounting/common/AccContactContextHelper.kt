package ok.logvidmi.accounting.common

import ok.logvidmi.accounting.common.models.AccContact
import ok.logvidmi.accounting.common.models.AccContactFilter

fun AccContact.deepCopy() = AccContact(id, name)

fun AccContactFilter.deepCopy() = AccContactFilter(searchString)