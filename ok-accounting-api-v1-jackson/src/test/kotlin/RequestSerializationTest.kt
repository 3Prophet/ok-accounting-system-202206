import org.junit.Test
import ru.otus.logvidmi.accounting.api.v1.apiV1Mapper
import ru.otus.logvidmi.accounting.api.v1.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val request = ContactCreateRequest(
        requestId = "123",
        debug = ContactDebug(
            mode = ContactRequestDebugMode.STUB,
            stub = ContactRequestDebugStubs.BAD_TITLE
        ),
        contact = ContactCreateObject(
            name = "contact name"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"contact name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ContactCreateRequest

        assertEquals(request, obj)
    }
}