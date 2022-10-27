package ru.otus.logvidmi.accounting.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer

/**
 * Start message processing
 */
fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()))
    consumer.run()
}