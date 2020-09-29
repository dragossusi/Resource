package ro.dragossusi.observer

import ro.dragossusi.resource.DataResource
import ro.rachieru.dragos.errordata.handler.ErrorDataHandler


/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
open class DataResourceObserver<T>(
    errorDataHandler: ErrorDataHandler? = null
) : BaseResourceObserver<DataResource<T>>(errorDataHandler) {

    override fun onSuccessStatus(resource: DataResource<T>) {
        onSuccess(resource.data)
    }

    open fun onSuccess(data: T?) {}

}