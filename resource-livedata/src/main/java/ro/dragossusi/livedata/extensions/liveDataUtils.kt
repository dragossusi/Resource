package ro.dragossusi.livedata.extensions

import androidx.lifecycle.liveData
import ro.dragossusi.livedata.CompletionLiveData
import ro.dragossusi.livedata.ResourceLiveData
import ro.dragossusi.logger.TagLogger
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
    logger: TagLogger? = null,
    block: suspend () -> T
): ResourceLiveData<T> = liveData(context) {
    try {
        emit(DataResource.Loading<T>())
        val data = block()
        emit(DataResource.Success(data))
    } catch (t: Throwable) {
        logger?.e(t)
        emit(DataResource.Error<T>(t))
    }
}

internal fun completionLiveData(
    context: CoroutineContext,
    logger: TagLogger? = null,
    block: suspend () -> Unit
): CompletionLiveData = liveData(context) {
    try {
        emit(CompletionResource.Loading)
        block()
        emit(CompletionResource.Completed)
    } catch (t: Throwable) {
        logger?.e(t)
        emit(CompletionResource.Error(t))
    }
}