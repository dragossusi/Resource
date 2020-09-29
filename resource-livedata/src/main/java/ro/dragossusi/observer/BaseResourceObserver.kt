package ro.dragossusi.observer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.ResourceStatus
import ro.rachieru.dragos.errordata.ErrorData
import ro.rachieru.dragos.errordata.handler.ErrorDataHandler

/**
 *
 * @author Dragos
 * @since 06.07.2020
 */
abstract class BaseResourceObserver<T : CompletionResource>(
    val errorDataHandler: ErrorDataHandler?
) : Observer<T?> {

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
                if (errorDataHandler != null && errorDataHandler.routeErrorData(error)) return
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

    protected open fun onFinished(success: Boolean) {}

    protected open fun onFailure(error: ErrorData) {
    }

}