package ro.dragossusi

import ro.dragossusi.flow.extensions.completionFlow
import ro.dragossusi.flow.extensions.resourceFlow
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