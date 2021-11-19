package ro.dragossusi.resource.flow.extensions

import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ro.dragossusi.resource.*
import ro.dragossusi.resource.flow.DataFlow
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 15.06.2020
 */
fun <T> Flow<T>.logErrors(message: String, tag: String? = null): Flow<T> {
    return catch {
        Napier.e(message, it, tag)
    }
}

fun <T> Flow<T>.startWithNull(): Flow<T?> {
    return onStart<T?> {
        emit(null)
    }
}

fun <R : Resource> Flow<R>.onError(
    body: OnFailureListener
): Flow<R> {
    return onEach {
        if (it is CompletionResource.Error)
            body.onFailure(it.error)
        else if (it is DataResource.Error<*>)
            body.onFailure(it.error)
    }
}

/**
 * Execute on success
 */
fun <T> Flow<DataResource<T>>.onSuccess(
    body: OnSuccessListener<T>
): Flow<DataResource<T>> = onEach {
    if (it is DataResource.Success)
        body.onSuccess(it.data)
}.flowOn(Dispatchers.Main)

/**
 * Execute on finish
 */
fun <R : Resource> Flow<R>.onFinish(
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
    context: CoroutineContext = Dispatchers.Default,
    logErrors: Boolean = true,
    body: suspend () -> T
): Flow<DataResource<T>> = flow {
    try {
        val result: T = body()
        emit(DataResource.Success(result))
    } catch (e: Exception) {
        //log error
        if (logErrors)
            Napier.e("resourceFlow Error", e)
        //create resource
        val resource = DataResource.Error<T>(e)
        //emit resource
        emit(resource)
    }
}.flowOn(context)

internal fun completionFlow(
    context: CoroutineContext = Dispatchers.Default,
    logErrors: Boolean = true,
    body: suspend () -> Unit
): Flow<CompletionResource> = flow {
    try {
        body()
        emit(CompletionResource.Completed)
    } catch (e: Exception) {
        //log error
        if (logErrors)
            Napier.e("completionFlow Error", e)
        //create resource
        val resource = CompletionResource.Error(e)
        //emit resource
        emit(resource)
    }
}.flowOn(context)


fun <T> Flow<List<T>>.firstPageEmpty(
    page: Long
) = onStart {
    if (page == 1L)
        emit(emptyList())
}

fun DataFlow<*>.toCompletionFlow() = map {
    it.toCompletion()
}