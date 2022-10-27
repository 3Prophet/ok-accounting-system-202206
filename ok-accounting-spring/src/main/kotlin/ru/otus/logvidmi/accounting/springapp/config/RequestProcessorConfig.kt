package ru.otus.logvidmi.accounting.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.logvidmi.accounting.business.AccContactProcessor

@Configuration
class RequestProcessorConfig {

    @Bean
    fun processor(): AccContactProcessor = AccContactProcessor()

}