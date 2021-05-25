package ro.dragossusi.resource.flow.observer

import kotlinx.coroutines.flow.Flow

fun interface FlowObserver<T> {
    fun observe(flow: Flow<T>): Flow<T>
}