package ro.dragossusi.resource.flow.observer

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.OnCompletedListener
import ro.dragossusi.resource.flow.extensions.onCompleted

class CompletionResourceObserver(
    errorHandler: MessageDataHandler?,
    setup: CompletionResourceObserver.() -> Unit
) : ResourceObserver<CompletionResource>(errorHandler) {

    private val onCompletedListeners = mutableListOf<OnCompletedListener>()

    fun onCompleted(listener: OnCompletedListener) {
        onCompletedListeners += listener
    }

    private fun onCompleted() {
        onCompletedListeners.forEach {
            it.onCompleted()
        }
    }

    override fun observe(flow: Flow<CompletionResource>): Flow<CompletionResource> {
        return super.observe(flow).onCompleted {
            onCompleted()
        }
    }

    init {
        setup.invoke(this)
    }

}