package ro.dragossusi.flow.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ro.dragossusi.flow.CompletionFlow
import ro.dragossusi.flow.ResourceFlow
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.ResourceStatus
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 15.06.2020
 */
fun <T> dataResourceFlow(
    context: CoroutineContext,
    block: suspend () -> DataResource<T>
): ResourceFlow<T> = flow {
    try {
        (DataResource.loading<T>())
        emit(block())
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(DataResource.error<T>(t))
    }
}.flowOn(context)

fun completionResourceFlow(
    context: CoroutineContext,
    block: suspend () -> CompletionResource
): CompletionFlow = flow {
    try {
        emit(CompletionResource.loading())
        emit(block())
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(CompletionResource.error(t))
    }
}.flowOn(context)

fun <T, R> Flow<DataResource<T>?>.mapSuccess(
    transform: T.() -> DataResource<R>
): Flow<DataResource<R>?> {
    return map {
        if (it == null) return@map null
        when (it.status) {
            ResourceStatus.SUCCESS -> transform(it.requireData())
            ResourceStatus.ERROR -> DataResource.error(it.requireError())
            ResourceStatus.LOADING -> DataResource.loading()
        }
    }
}