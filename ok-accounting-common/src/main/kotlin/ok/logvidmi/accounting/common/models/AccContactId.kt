package ok.logvidmi.accounting.common.models

@JvmInline
value class AccContactId(private val id: String) {

    fun asString() = id

    companion object {
        val NONE = AccContactId("")
    }
}