package ru.otus.logvidmi.accounting.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>): RuntimeException("Class $clazz cannot be mapped to AccContext")