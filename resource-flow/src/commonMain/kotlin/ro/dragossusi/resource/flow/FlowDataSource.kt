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
    logErrors: Boolean = true,
    block: suspend () -> T
) = resourceFlow(context, logErrors, block)

fun FlowDataSource.completionFlow(
    logErrors: Boolean = true,
    block: suspend () -> Unit
) = completionFlow(context, logErrors, block)