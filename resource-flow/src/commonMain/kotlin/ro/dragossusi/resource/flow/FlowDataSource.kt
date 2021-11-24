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

    val tagLogger: TagLogger?

}

fun <T> FlowDataSource.resourceFlow(
    logger: TagLogger? = tagLogger,
    block: suspend () -> T
) = resourceFlow(context, logger, block)

fun FlowDataSource.completionFlow(
    logger: TagLogger? = tagLogger,
    block: suspend () -> Unit
) = completionFlow(context, logger, block)