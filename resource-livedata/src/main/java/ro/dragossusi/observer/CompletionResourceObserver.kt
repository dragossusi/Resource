package ro.dragossusi.observer

import ro.dragossusi.resource.CompletionResource
import ro.rachieru.dragos.errordata.handler.ErrorDataHandler

/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
abstract class CompletionResourceObserver(
    errorDataHandler: ErrorDataHandler?
) : BaseResourceObserver<CompletionResource>(errorDataHandler) {

    override fun onSuccessStatus(resource: CompletionResource) {
        onCompleted()
    }

    protected open fun onCompleted() {}

}