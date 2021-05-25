package ro.dragossusi.resource.flow.extensions

import android.media.MediaSync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.error.MessageDataException
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.OnFailureListener
import ro.dragossusi.resource.OnSuccessListener
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 15.06.2020
 */
fun <T> Flow<T>.logErrors(): Flow<T> {
    return catch {
        Timber.e(it)
    }
}

fun <T> Flow<T>.startWithNull(): Flow<T?> {
    return onStart<T?> {
        emit(null)
    }
}

fun <T : CompletionResource> Flow<T>.onError(
    body: OnFailureListener
): Flow<T> {
    return onEach {
        if (it.isFailed)
            body.onFailure(it.error)
    }
}

/**
 * Execute on success
 */
fun <T> Flow<DataResource<T>>.onSuccess(
    body: OnSuccessListener<T>
): Flow<DataResource<T>> = onEach {
    if (it.isSuccessful)
        body.onSuccess(it.data)
}.flowOn(Dispatchers.Main)

/**
 * Execute on finish
 */
fun <R : CompletionResource> Flow<R>.onFinish(
    body: suspend (Boolean) -> Unit
): Flow<R> = onEach {
    if (!it.isLoading)
        body(it.isSuccessful)
}.flowOn(Dispatchers.Main)

/**
 * Execute on completed
 */
fun <T : CompletionResource> Flow<T>.onCompleted(
    body: suspend () -> Unit
): Flow<T> = onEach {
    if (it.isSuccessful)
        body()
}.flowOn(Dispatchers.Main)


internal fun <T> resourceFlow(
    context: CoroutineContext = Dispatchers.IO,
    body: suspend () -> T
): Flow<DataResource<T>> {
    val flow = flow {
        try {
            emit(DataResource.success(body()))
        } catch (e: Exception) {
            Timber.e(e)
            emit(DataResource.error<T>(e))
        }
    }
    return flow.flowOn(context)
        .catch {
            Timber.e(it)
            //create resource
            val resource: DataResource<T> =
                if (it is MessageDataException) DataResource.error(it.messageData)
                else DataResource.error(it)
            //emit it
            emit(resource)
        }
}

internal fun completionFlow(
    context: CoroutineContext = Dispatchers.IO,
    body: suspend () -> Unit
): Flow<CompletionResource> {
    return flow {
        body()
        emit(CompletionResource.completed())
    }.flowOn(context)
        .catch {
            Timber.e(it)
            //create resource
            val resource =
                if (it is MessageDataException) CompletionResource.error(it.messageData)
                else CompletionResource.error(it)
            //emit it
            emit(resource)
        }
}


fun <T> Flow<List<T>>.firstPageEmpty(
    page: Long
) = onStart {
    if (page == 1L)
        emit(emptyList())
}