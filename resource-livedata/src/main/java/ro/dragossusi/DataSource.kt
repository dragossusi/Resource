package ro.dragossusi

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
    block: suspend () -> DataResource<T>
) = dataResourceLiveData<T>(context, block)

fun DataSource.completionResourceLiveData(
    block: suspend () -> CompletionResource
) = completionResourceLiveData(context, block)