package ro.dragossusi.livedata.extensions

import androidx.lifecycle.*
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
fun <T> dataResourceLiveData(
    context: CoroutineContext,
    block: suspend LiveDataScope<DataResource<T>?>.() -> Unit
): ResourceLiveData<T> = liveData(context) {
    try {
        emit(DataResource.loading())
        this.block()
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(DataResource.error<T>(t))
    }
}

fun completionResourceLiveData(
    context: CoroutineContext,
    block: suspend LiveDataScope<CompletionResource?>.() -> Unit
): CompletionLiveData = liveData(context) {
    try {
        emit(CompletionResource.loading())
        this.block()
    } catch (t: Throwable) {
        t.printStackTrace()
        emit(CompletionResource.error(t))
    }
}

fun <T> staticLiveData(value: T?): LiveData<T?> = MutableLiveData(value)

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