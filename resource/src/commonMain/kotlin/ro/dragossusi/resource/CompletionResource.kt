package ro.dragossusi.resource

import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.StringMessageData
import ro.dragossusi.messagedata.toMessageData
import kotlin.jvm.JvmStatic

/**
 *
 * @author Dragos
 * @since 26.06.2020
 */
sealed class CompletionResource(
    override val status: ResourceStatus
) : Resource {

    override fun requireError(): MessageData {
        if (this !is Error) {
            throw IllegalStateException("The resource has status $status")
        }
        return error ?: throw Exception("Error null, status: $status")
    }

    object Completed : CompletionResource(ResourceStatus.SUCCESS)
    object Loading : CompletionResource(ResourceStatus.LOADING)
    data class Error(val error: MessageData?) : CompletionResource(ResourceStatus.ERROR)


    companion object {
        @JvmStatic
        fun Error(message: String) = Error(
            error = StringMessageData(message)
        )

        @JvmStatic
        fun Error(throwable: Throwable) = Error(
            error = throwable.toMessageData()
        )
    }

}