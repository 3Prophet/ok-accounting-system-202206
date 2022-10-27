package ru.otus.logvidmi.accounting.kafka

import ok.logvidmi.accounting.common.AccContactContext
import ru.otus.logvidmi.accounting.api.v1.apiV1RequestDeserialize
import ru.otus.logvidmi.accounting.api.v1.apiV1ResponseSerialize
import ru.otus.logvidmi.accounting.api.v1.models.IRequest
import ru.otus.logvidmi.accounting.api.v1.models.IResponse
import ru.otus.logvidmi.accounting.mappers.v1.fromTransport
import ru.otus.logvidmi.accounting.mappers.v1.toTransportContact

class ConsumerStrategyV1: ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(ctx: AccContactContext): String {
        val response: IResponse = ctx.toTransportContact()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(transport: String, ctx: AccContactContext) {
        val request: IRequest = apiV1RequestDeserialize(transport)
        ctx.fromTransport(request)
    }
}