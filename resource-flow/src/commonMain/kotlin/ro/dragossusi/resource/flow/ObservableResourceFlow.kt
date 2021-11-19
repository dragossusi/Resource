package ro.dragossusi.resource.flow

import kotlinx.coroutines.flow.*
import ro.dragossusi.resource.Resource
import ro.dragossusi.resource.flow.extensions.onLoadingChanged

class ObservableResourceFlow<R : Resource>() {

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow = _loadingFlow.asStateFlow()

    private val changeFlow = MutableStateFlow<Flow<R>>(emptyFlow())

    val flow: Flow<R> = changeFlow.flattenConcat()
        .onLoadingChanged { _loadingFlow.value = it }

    fun setSource(source: Flow<R>) {
        changeFlow.value = source
    }

    fun clear() {
        changeFlow.value = emptyFlow()
    }

}