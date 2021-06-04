package ro.dragossusi.resource.flow.observer

import ro.dragossusi.messagedata.handler.MessageDataHandler

fun <T> dataObserver(
    errorHandler: MessageDataHandler?,
    setup: DataResourceObserver<T>.() -> Unit
) = DataResourceObserver(errorHandler, setup)

fun completionObserver(
    errorHandler: MessageDataHandler?,
    setup: CompletionResourceObserver.() -> Unit
) = CompletionResourceObserver(errorHandler, setup)