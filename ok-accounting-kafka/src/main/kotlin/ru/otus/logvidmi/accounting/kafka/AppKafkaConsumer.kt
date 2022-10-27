package ru.otus.logvidmi.accounting.kafka

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import mu.KotlinLogging
import ok.logvidmi.accounting.common.AccContactContext
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.logvidmi.accounting.business.AccContactProcessor
import java.time.Duration
import java.util.*

data class InputOutputTopics(val input: String, val output: String)

interface ConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics
    fun serialize(ctx: AccContactContext): String
    fun deserialize(transport: String, ctx: AccContactContext)
}

private data class TopicsAndStrategy(
    val inputTopic: String,
    val outputTopic: String,
    val strategy: ConsumerStrategy
)

private val log = KotlinLogging.logger {}

class AppKafkaConsumer(
    private val config: AppKafkaConfig,
    consumerStrategies: List<ConsumerStrategy>,
    private val processor: AccContactProcessor = AccContactProcessor(),
    private val consumer: Consumer<String, String> = config.createKafkaConsumer(),
    private val producer: Producer<String, String> = config.createKafkaProducer(),
) {
    private val process = atomic(true)
    private val topicsAndStrategyByInputTopic = consumerStrategies.associate {
        val topics = it.topics(config)
        Pair(
            topics.input,
            TopicsAndStrategy(topics.input, topics.output, it)
        )
    }

    fun run() = runBlocking {
        try {
            consumer.subscribe(topicsAndStrategyByInputTopic.keys)
            while (process.value) {
                val ctx = AccContactContext(timeStart = Clock.System.now())
                val records: ConsumerRecords<String, String> = withContext(Dispatchers.IO) {
                    consumer.poll(Duration.ofSeconds(1))
                }
                if (!records.isEmpty) {
                    log.info { "Received message count: ${records.count()}" }
                }

                records.forEach { record: ConsumerRecord<String, String> ->
                    try {
                        log.info { "Process ${record.key()} from ${record.topic()}:\n${record.value()}" }
                        val (_, outputTopic, strategy) = topicsAndStrategyByInputTopic[record.topic()]
                            ?: throw RuntimeException("Message from unknown topic: ${record.topic()}")
                        strategy.deserialize(record.value(), ctx)
                        processor.exec(ctx)
                        sendResponse(outputTopic, strategy, ctx)


                    } catch (ex: Exception) {
                        log.error(ex) { "Error" }
                    }
                }
            }
        } catch (ex: WakeupException) {

        } catch (ex: RuntimeException) {
            withContext(NonCancellable) {
                throw ex
            }

        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private fun sendResponse(
        outputTopic: String,
        strategy: ConsumerStrategy,
        ctx: AccContactContext
    ) {

        val json = strategy.serialize(ctx)
        val resRecord = ProducerRecord(
            outputTopic,
            UUID.randomUUID().toString(), json
        )
        log.info { "Sending ${resRecord.key()} to output topic:${resRecord.topic()}:\n$json"}
        producer.send(resRecord)
    }

    fun stop() {
        process.value = false
    }
}

