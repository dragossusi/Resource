package ro.dragossusi.observer

import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.OnCompletedListener
import ro.dragossusi.resource.OnFailureListener
import ro.dragossusi.resource.OnSuccessListener


/**
 *
 * @author Dragos
 * @since 10.06.2020
 */
class DataResourceObserver<T>(
    errorDataHandler: MessageDataHandler? = null
) : BaseResourceObserver<DataResource<T>>(errorDataHandler) {

    private val onSuccessListeners = mutableListOf<OnSuccessListener<T>>()

    fun addOnSuccessListener(listener: OnSuccessListener<T>) {
        onSuccessListeners += listener
    }

    override fun onSuccessStatus(resource: DataResource<T>) {
        val successResource = resource as DataResource.Success<T>
        onSuccess(resource.data)
    }

    private fun onSuccess(data: T?) {
        onSuccessListeners.forEach {
            it.onSuccess(data)
        }
    }

}

@Suppress("unused")
fun <T> DataResourceObserver<T>.onSuccess(
    listener: OnSuccessListener<T>
): DataResourceObserver<T> = apply {
    addOnSuccessListener(listener)
}