package ro.dragossusi

import ro.dragossusi.livedata.extensions.completionLiveData
import ro.dragossusi.livedata.extensions.resourceLiveData
import ro.dragossusi.logger.TagLogger
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Dragos
 * @since 18.08.2020
 */
interface DataSource {

    val context: CoroutineContext

    val tagLogger: TagLogger?

}

fun <T> DataSource.resourceLiveData(
    logger: TagLogger? = tagLogger,
    block: suspend () -> T
) = resourceLiveData(context,logger, block)

fun DataSource.completionLiveData(
    logger: TagLogger? = tagLogger,
    block: suspend () -> Unit
) = completionLiveData(context,logger, block)