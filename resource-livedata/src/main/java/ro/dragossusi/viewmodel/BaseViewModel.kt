package ro.dragossusi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ro.dragossusi.livedata.LoadingLiveData
import ro.dragossusi.livedata.ObservableLiveData
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.Resource


/**
 *
 * @since 9/23/20
 * @author dragos
 */
abstract class BaseViewModel : ViewModel() {

    protected val _loadingLiveData = LoadingLiveData()

    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    protected fun <T> observableData(): ObservableLiveData<DataResource<T>> {
        val observable = ObservableLiveData<DataResource<T>>()
        addLoadingSource(observable)
        return observable
    }

    protected fun observableCompletion(): ObservableLiveData<CompletionResource> {
        val observable = ObservableLiveData<CompletionResource>()
        addLoadingSource(observable)
        return observable
    }

    fun addLoadingSource(source: LiveData<out Resource?>) {
        _loadingLiveData += source
    }

    fun removeLoadingSource(source: LiveData<out CompletionResource?>) {
        _loadingLiveData -= source
    }

}