package ro.dragossusi.flow.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ro.dragossusi.flow.CompletionFlow
import ro.dragossusi.flow.ResourceFlow
import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.error.MessageDataException
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.ResourceStatus
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

fun <T : CompletionResource> Flow<T>.onError(body: suspend (MessageData?) -> Unit): Flow<T> {
    return onEach {
        if (it.isFailed)
            body(it.error)
    }
}

/**
 * Execute on success
 */
fun <T> Flow<DataResource<T>>.onSuccess(
    body: suspend (T?) -> Unit
): Flow<DataResource<T>> = onEach {
    if (it.isSuccessful)
        body(it.data)
}.flowOn(Dispatchers.Main)

/**
 * Execute on finish
 */
fun <T> Flow<DataResource<T>>.onFinish(
    body: suspend (DataResource<T>) -> Unit
): Flow<DataResource<T>> = onEach {
    if (!it.isLoading)
        body(it)
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


fun <T> resourceFlow(
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

fun completionFlow(
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