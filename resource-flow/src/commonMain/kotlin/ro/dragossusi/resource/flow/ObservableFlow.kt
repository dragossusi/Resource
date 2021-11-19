package ro.dragossusi.resource.flow

import kotlinx.coroutines.flow.*

@Deprecated("don't use this, use SignalFlow", level = DeprecationLevel.ERROR)
class ObservableFlow<T>() {

    private val changeFlow = MutableStateFlow<Flow<T>>(emptyFlow())

    val flow: Flow<T> = changeFlow.flattenConcat()

    fun setSource(source: Flow<T>) {
        changeFlow.value = source
    }

    fun clear() {
        changeFlow.value = emptyFlow()
    }

}