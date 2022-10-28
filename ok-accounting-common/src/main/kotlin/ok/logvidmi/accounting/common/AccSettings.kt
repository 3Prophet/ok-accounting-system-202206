package ok.logvidmi.accounting.common

import ok.logvidmi.accounting.common.repo.IContactRepository

data class AccSettings(
    val testRepo: IContactRepository = IContactRepository.NONE,
    val prodRepo: IContactRepository = IContactRepository.NONE,
)