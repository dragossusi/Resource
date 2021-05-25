package ro.dragossusi.livedata.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import ro.dragossusi.livedata.CompletionLiveData
import ro.dragossusi.livedata.ResourceLiveData
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.ResourceStatus
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
        emit(DataResource.loading())
        val data = block()
        emit(DataResource.success(data))
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(DataResource.error<T>(t))
    }
}

internal fun completionLiveData(
    context: CoroutineContext,
    block: suspend () -> Unit
): CompletionLiveData = liveData(context) {
    try {
        emit(CompletionResource.loading())
        block()
        emit(CompletionResource.completed())
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(CompletionResource.error(t))
    }
}

fun <T, R> LiveData<DataResource<T>?>.mapSuccess(
    transform: T.() -> DataResource<R>
): LiveData<DataResource<R>?> {
    return Transformations.map(this) {
        if (it == null) return@map null
        when (it.status) {
            ResourceStatus.SUCCESS -> transform(it.requireData())
            ResourceStatus.ERROR -> DataResource.error(it.requireError())
            ResourceStatus.LOADING -> DataResource.loading()
        }
    }
}