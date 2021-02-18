package ro.dragossusi

import ro.dragossusi.flow.extensions.completionResourceFlow
import ro.dragossusi.flow.extensions.dataResourceFlow
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

fun <T> DataSource.dataResourceFlow(
    block: suspend () -> DataResource<T>
) = dataResourceFlow<T>(context, block)

fun DataSource.completionResourceFlow(
    block: suspend () -> CompletionResource
) = completionResourceFlow(context, block)