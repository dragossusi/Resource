package ro.dragossusi.resource

import kotlinx.coroutines.flow.*
import ro.dragossusi.CommonViewModel
import ro.dragossusi.coroutineScope
import ro.dragossusi.resource.flow.ObservableFlow
import ro.dragossusi.resource.flow.ResourceFlow

@Deprecated("loading is not tested")
abstract class BaseViewModel : CommonViewModel() {

    private val requests = MutableStateFlow<List<ResourceFlow>>(emptyList())

    protected val _loadingLiveData: StateFlow<Boolean> = requests.flatMapLatest {
        if (it.isEmpty()) flowOf(false)
        else {
            combine(*it.toTypedArray()) { resources ->
                resources.any {
                    it.isLoading
                }
            }
        }
    }.stateIn(coroutineScope, SharingStarted.Eagerly, false)

    @Suppress("unused")
    val loadingLiveData: StateFlow<Boolean> = _loadingLiveData

    @Suppress("unused")
    protected fun <T> observableData() = ObservableFlow<DataResource<T>>().also {
        addLoadingSource(it.flow)
    }

    @Suppress("unused")
    protected fun observableCompletion() = ObservableFlow<CompletionResource>().also {
        addLoadingSource(it.flow)
    }


    fun addLoadingSource(source: ResourceFlow) {
        requests.value += (requests.value + source)
    }

    fun removeLoadingSource(source: ResourceFlow) {
        requests.value = (requests.value - source)
    }

}