package ro.dragossusi.resource.flow.observer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.OnFailureListener
import ro.dragossusi.resource.OnFinishListener
import ro.dragossusi.resource.Resource
import ro.dragossusi.resource.flow.extensions.onError
import ro.dragossusi.resource.flow.extensions.onFinish

open class ResourceObserver<T : Resource>(
    private val errorHandler: MessageDataHandler?,
) : FlowObserver<T> {

    private val onFinishListeners = mutableListOf<OnFinishListener>()
    private val onFailureListeners = mutableListOf<OnFailureListener>()

    @Suppress("unused")
    fun onFinish(listener: OnFinishListener) {
        onFinishListeners += listener
    }

    @Suppress("unused")
    fun onFailure(listener: OnFailureListener) {
        onFailureListeners += listener
    }

    override fun observe(flow: Flow<T>): Flow<T> {
        return flow.onError {
            if (it != null) {
                errorHandler?.routeMessageData(it)
            }
            onFailure(it)
        }.onFinish {
            onFinished(it)
        }
    }

    protected fun onFinished(success: Boolean) {
        onFinishListeners.forEach {
            it.onFinish(success)
        }
    }

    protected fun onFailure(error: MessageData?) {
        onFailureListeners.forEach {
            it.onFailure(error)
        }
    }

}

fun <R : CompletionResource> Flow<R>.observe(
    scope: CoroutineScope,
    observer: ResourceObserver<R>
) {
    scope.launch {
        observer.observe(this@observe)
            .collect()
    }
}