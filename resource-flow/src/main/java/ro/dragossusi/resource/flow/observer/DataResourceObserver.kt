package ro.dragossusi.resource.flow.observer

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.OnSuccessListener
import ro.dragossusi.resource.flow.extensions.onSuccess
import ro.dragossusi.sevens.android.livedata.observer.ResourceObserver

class DataResourceObserver<T>(
    errorHandler: MessageDataHandler?,
    setup: DataResourceObserver<T>.() -> Unit
) : ResourceObserver<DataResource<T>>(errorHandler) {

    private val onSuccessListeners = mutableListOf<OnSuccessListener<T>>()

    fun onSuccess(listener: OnSuccessListener<T>) {
        onSuccessListeners += listener
    }

    override fun observe(flow: Flow<DataResource<T>>): Flow<DataResource<T>> {
        return super.observe(flow).onSuccess {
            onSuccess(it)
        }
    }

    private fun onSuccess(data: T?) {
        onSuccessListeners.forEach {
            it.onSuccess(data)
        }
    }

    init {
        setup.invoke(this)
    }

}