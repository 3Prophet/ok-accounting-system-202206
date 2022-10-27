package ru.otus.logvidmi.accounting.cor.handlers

abstract class AbstractCorExec<T>(
    override val title: String,
    override val description: String = "",
    val blockOn: suspend T.() -> Boolean = { true },
    val blockExcept: suspend T.(Throwable) -> Unit = {},
): ICorExec<T> {
    protected abstract suspend fun handle(context: T)

    private suspend fun on(context: T): Boolean = context.blockOn()
    private suspend fun except(context: T, e: Throwable) = context.blockExcept(e)

    override suspend fun exec(context: T) {
        if (on(context)) {
            try {
                handle(context)
            } catch (e: Throwable) {
                except(context, e)
            }
        }
    }
}