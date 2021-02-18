package ro.dragossusi.observer

import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.DataResource


/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
open class DataResourceObserver<T>(
    errorDataHandler: MessageDataHandler? = null
) : BaseResourceObserver<DataResource<T>>(errorDataHandler) {

    override fun onSuccessStatus(resource: DataResource<T>) {
        onSuccess(resource.data)
    }

    open fun onSuccess(data: T?) {}

}