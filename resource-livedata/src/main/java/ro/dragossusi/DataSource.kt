package ro.dragossusi

import androidx.lifecycle.LiveDataScope
import ro.dragossusi.livedata.extensions.completionResourceLiveData
import ro.dragossusi.livedata.extensions.dataResourceLiveData
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 18.08.2020
 */
interface DataSource {

    val context: CoroutineContext

}

fun <T> DataSource.dataResourceLiveData(
    block: suspend LiveDataScope<DataResource<T>?>.() -> Unit
) = dataResourceLiveData<T>(context, block)

fun DataSource.completionResourceLiveData(
    block: suspend LiveDataScope<CompletionResource?>.() -> Unit
) = completionResourceLiveData(context, block)