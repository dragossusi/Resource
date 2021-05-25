package ro.dragossusi.resource.flow

import ro.dragossusi.resource.flow.extensions.completionFlow
import ro.dragossusi.resource.flow.extensions.resourceFlow
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 18.08.2020
 */
interface FlowDataSource {

    val context: CoroutineContext

}

fun <T> FlowDataSource.resourceFlow(
    block: suspend () -> T
) = resourceFlow<T>(context, block)

fun FlowDataSource.completionFlow(
    block: suspend () -> Unit
) = completionFlow(context, block)