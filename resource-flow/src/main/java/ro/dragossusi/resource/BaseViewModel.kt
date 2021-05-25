package ro.dragossusi.resource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.dragossusi.resource.flow.ObservableFlow

abstract class BaseViewModel : ViewModel() {

    protected val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData
        get() = _loadingLiveData

    protected fun <T> observableData() = ObservableFlow<DataResource<T>>()
    protected fun observableCompletion() = ObservableFlow<CompletionResource>()

}