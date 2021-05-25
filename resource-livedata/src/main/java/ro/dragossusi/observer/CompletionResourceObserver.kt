package ro.dragossusi.observer

import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.OnCompletedListener

/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
class CompletionResourceObserver(
    messageDataHandler: MessageDataHandler?
) : BaseResourceObserver<CompletionResource>(messageDataHandler) {

    private val onCompletedListeners = mutableListOf<OnCompletedListener>()

    override fun onSuccessStatus(resource: CompletionResource) {
        onCompleted()
    }

    private fun onCompleted() {
        onCompletedListeners.forEach {
            it.onCompleted()
        }
    }

}