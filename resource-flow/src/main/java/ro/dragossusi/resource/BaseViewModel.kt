package ro.dragossusi.resource

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import ro.dragossusi.resource.flow.CompletionFlow
import ro.dragossusi.resource.flow.ObservableFlow

@Deprecated("loading is not tested")
abstract class BaseViewModel : ViewModel() {

    private val requests = MutableStateFlow<List<CompletionFlow>>(emptyList())

    protected val _loadingLiveData: StateFlow<Boolean> = requests.flatMapLatest {
        if (it.isEmpty()) flowOf(false)
        else {
            combine(*it.toTypedArray()) { resources ->
                resources.any {
                    it.isLoading
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val loadingLiveData = _loadingLiveData.asLiveData()

    protected fun <T> observableData() = ObservableFlow<DataResource<T>>().also {
        addLoadingSource(it.flow)
    }

    protected fun observableCompletion() = ObservableFlow<CompletionResource>().also {
        addLoadingSource(it.flow)
    }


    fun addLoadingSource(source: Flow<CompletionResource>) {
        requests.value += (requests.value + source)
    }

    fun removeLoadingSource(source: Flow<CompletionResource>) {
        requests.value = (requests.value - source)
    }

}