package ru.otus.logvidmi.accounting.mappers.v1.exceptions

import ok.logvidmi.accounting.common.models.AccCommand

class UnknownAccCommand(command: AccCommand) : Throwable("Wrong command $command at mapping toTransport stage")