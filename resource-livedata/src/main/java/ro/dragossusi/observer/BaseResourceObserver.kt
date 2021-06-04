package ro.dragossusi.observer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.OnFailureListener
import ro.dragossusi.resource.OnFinishListener
import ro.dragossusi.resource.ResourceStatus

/**
 *
 * @author Dragos
 * @since 06.07.2020
 */
abstract class BaseResourceObserver<T : CompletionResource>(
    val messageDataHandler: MessageDataHandler?
) : Observer<T?> {

    private val onFinishListeners = mutableListOf<OnFinishListener>()
    private val onFailureListeners = mutableListOf<OnFailureListener>()

    fun addOnFinishListener(listener: OnFinishListener) {
        onFinishListeners += listener
    }

    fun addOnFailureListener(listener: OnFailureListener) {
        onFailureListeners += listener
    }

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    final override fun onChanged(resource: T?) {
        val isLoading = resource?.isLoading == true
        _loadingLiveData.value = isLoading
        onLoadingChanged(isLoading)
        if (resource == null) return
        when (resource.status) {
            ResourceStatus.ERROR -> {
                onFinished(false)
                val error = resource.requireError()
                if (messageDataHandler != null && messageDataHandler.routeErrorData(error)) return
                onFailure(error)
            }
            ResourceStatus.SUCCESS -> {
                onFinished(true)
                onSuccessStatus(resource)
            }
        }
    }

    open fun onLoadingChanged(isLoading: Boolean) {}

    protected abstract fun onSuccessStatus(resource: T)

    protected fun onFinished(success: Boolean) {
        onFinishListeners.forEach {
            it.onFinish(success)
        }
    }

    protected fun onFailure(error: MessageData) {
        onFailureListeners.forEach {
            it.onFailure(error)
        }
    }

}

fun <T, O : BaseResourceObserver<T>> O.onFinish(
    listener: OnFinishListener
) = apply {
    addOnFinishListener(listener)
}

fun <T, O : BaseResourceObserver<T>> O.onFailure(
    listener: OnFailureListener
) = apply {
    addOnFailureListener(listener)
}