package ru.otus.logvidmi.accounting.springapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OkAccountingSpringApplication

fun main(args: Array<String>) {
	runApplication<OkAccountingSpringApplication>(*args)
}
