package ok.logvidmi.accounting.common.models

@JvmInline
value class AccRequestId(private val id: String) {

    fun asString() = id

    companion object {
        val NONE = AccRequestId("")
    }
}