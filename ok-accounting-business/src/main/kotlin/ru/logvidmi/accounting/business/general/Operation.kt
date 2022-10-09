package ru.logvidmi.accounting.business.general

import ok.logvidmi.accounting.common.AccContactContext
import ok.logvidmi.accounting.common.models.AccCommand
import ru.otus.logvidmi.accounting.cor.ICorChainDsl
import ru.otus.logvidmi.accounting.cor.chain

fun ICorChainDsl<AccContactContext>.operation(
    title: String,
    command: AccCommand,
    dsl: ICorChainDsl<AccContactContext>.() -> Unit
) =
    chain {
        on {
            this.command == command
        }
        this.title = title
        dsl()
    }