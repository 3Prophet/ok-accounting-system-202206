package ru.otus.logvidmi.accounting.kafka

import kotlinx.coroutines.test.runTest
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.logvidmi.accounting.api.v1.apiV1RequestSerialize
import ru.otus.logvidmi.accounting.api.v1.apiV1ResponseDeserialize
import ru.otus.logvidmi.accounting.api.v1.models.*
import java.util.*
import javax.swing.text.html.HTML.Tag.OL

class KafkaConsumerStubTest {

    companion object {
        const val PARTITION = 0
    }

    @Test
    fun `calling contact create stub over kafka returns status ok and stub`() = runTest {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())

        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        ContactCreateRequest(
                            requestId = "111-111",
                            contact = ContactCreateObject(
                                name = "Some name"
                            ),
                            debug = ContactDebug(
                                mode = ContactRequestDebugMode.STUB,
                                stub = ContactRequestDebugStubs.SUCCESS
                            )
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val topicPartition = TopicPartition(inputTopic, PARTITION)
        startOffsets[topicPartition] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result: ContactCreateResponse = apiV1ResponseDeserialize(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("111-111", result.requestId)
        assertEquals("Some name", result.contact?.name)
    }
}