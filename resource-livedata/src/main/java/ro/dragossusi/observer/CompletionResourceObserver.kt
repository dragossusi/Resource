package ro.dragossusi.observer

import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource

/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
abstract class CompletionResourceObserver(
    messageDataHandler: MessageDataHandler?
) : BaseResourceObserver<CompletionResource>(messageDataHandler) {

    override fun onSuccessStatus(resource: CompletionResource) {
        onCompleted()
    }

    protected open fun onCompleted() {}

}