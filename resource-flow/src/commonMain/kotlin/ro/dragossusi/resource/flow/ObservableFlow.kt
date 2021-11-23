package ro.dragossusi.resource.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flattenConcat

class ObservableFlow<T> {

    private val changeFlow = MutableStateFlow<Flow<T>>(emptyFlow())

    @OptIn(FlowPreview::class)
    val flow: Flow<T> = changeFlow.flattenConcat()

    fun setSource(source: Flow<T>) {
        changeFlow.value = source
    }

    fun clear() {
        changeFlow.value = emptyFlow()
    }

}