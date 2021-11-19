package ro.dragossusi.resource.flow

import kotlinx.coroutines.flow.*

class ObservableFlow<T>() {

    private val changeFlow = MutableStateFlow<Flow<T>?>(null)

    val flow: Flow<T> = changeFlow.flatMapLatest {
        it ?: emptyFlow()
    }

    fun setSource(source: Flow<T>) {
        changeFlow.value = source
    }

    fun clear() {
        changeFlow.value = null
    }

}