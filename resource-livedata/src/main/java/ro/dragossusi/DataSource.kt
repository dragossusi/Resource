package ro.dragossusi

import ro.dragossusi.livedata.extensions.completionLiveData
import ro.dragossusi.livedata.extensions.resourceLiveData
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 18.08.2020
 */
interface DataSource {

    val context: CoroutineContext

}

fun <T> DataSource.resourceLiveData(
    block: suspend () -> T
) = resourceLiveData<T>(context, block)

fun DataSource.completionLiveData(
    block: suspend () -> Unit
) = completionLiveData(context, block)