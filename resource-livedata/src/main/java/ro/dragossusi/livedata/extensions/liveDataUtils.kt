package ro.dragossusi.livedata.extensions

import androidx.lifecycle.liveData
import ro.dragossusi.livedata.CompletionLiveData
import ro.dragossusi.livedata.ResourceLiveData
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 15.06.2020
 */
internal fun <T> resourceLiveData(
    context: CoroutineContext,
    block: suspend () -> T
): ResourceLiveData<T> = liveData(context) {
    try {
        emit(DataResource.Loading())
        val data = block()
        emit(DataResource.Success(data))
    } catch (t: Throwable) {
        emit(DataResource.Error(t))
    }
}

internal fun completionLiveData(
    context: CoroutineContext,
    block: suspend () -> Unit
): CompletionLiveData = liveData(context) {
    try {
        emit(CompletionResource.Loading)
        block()
        emit(CompletionResource.Completed)
    } catch (t: Throwable) {
        emit(CompletionResource.Error(t))
    }
}