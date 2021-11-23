package ro.dragossusi.resource.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ro.dragossusi.CommonViewModel
import ro.dragossusi.coroutineScope
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource

abstract class BaseViewModel : CommonViewModel() {

    private val requests = MutableStateFlow<List<Flow<Boolean>>>(emptyList())

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    protected val _loadingLiveData: StateFlow<Boolean> = requests.flatMapLatest { list ->
        if (list.isEmpty()) flowOf(false)
        else {
            combine(list) { resources ->
                resources.any { it }
            }
        }
    }.stateIn(coroutineScope, SharingStarted.Eagerly, false)

    @Suppress("unused")
    val loadingStateFlow: StateFlow<Boolean> = _loadingLiveData

    @Suppress("unused")
    protected fun <T> signalFlow() = SignalFlow<T>()


    @Suppress("unused")
    protected fun <T> observableData() = ObservableResourceFlow<DataResource<T>>().also {
        addLoadingSource(it.loadingFlow)
    }

    @Suppress("unused")
    protected fun observableCompletion() = ObservableResourceFlow<CompletionResource>().also {
        addLoadingSource(it.loadingFlow)
    }


    fun addLoadingSource(source: Flow<Boolean>) {
        requests.value += (requests.value + source)
    }

    @Suppress("unused")
    fun removeLoadingSource(source: Flow<Boolean>) {
        requests.value = (requests.value - source)
    }

}