package ro.dragossusi.observer

import kotlinx.coroutines.flow.flowOf
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.OnCompletedListener
import ro.dragossusi.resource.OnSuccessListener

/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
class CompletionResourceObserver(
    messageDataHandler: MessageDataHandler?
) : BaseResourceObserver<CompletionResource>(messageDataHandler) {

    private val onCompletedListeners = mutableListOf<OnCompletedListener>()

    fun addOnCompletedListener(listener: OnCompletedListener) {
        onCompletedListeners += listener
    }

    override fun onSuccessStatus(resource: CompletionResource) {
        onCompleted()
    }

    private fun onCompleted() {
        onCompletedListeners.forEach {
            it.onCompleted()
        }
    }

}

fun CompletionResourceObserver.onCompleted(
    listener: OnCompletedListener
): CompletionResourceObserver = apply {
    addOnCompletedListener(listener)
}