package ro.dragossusi.resource.flow

import ro.dragossusi.logger.TagLogger
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
    logger: TagLogger? = null,
    block: suspend () -> T
) = resourceFlow(context, logger, block)

fun FlowDataSource.completionFlow(
    logger: TagLogger? = null,
    block: suspend () -> Unit
) = completionFlow(context, logger, block)