package ro.dragossusi.resource

import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.StringMessageData
import ro.dragossusi.messagedata.ThrowableMessageData
import ro.dragossusi.messagedata.toMessageData

/**
 *
 * @author Dragos
 * @since 26.06.2020
 */
open class CompletionResource(
    val status: ResourceStatus,
    val error: MessageData?
) {

    val isSuccessful: Boolean
        get() = status == ResourceStatus.SUCCESS

    val isFailed: Boolean
        get() = status == ResourceStatus.ERROR

    val isLoading: Boolean
        get() = status == ResourceStatus.LOADING

    fun requireError(): MessageData = error ?: throw Exception("Error null, status: $status")

    companion object {

        @JvmStatic
        fun completed() =
            CompletionResource(
                status = ResourceStatus.SUCCESS,
                error = null
            )

        @JvmStatic
        fun loading() =
            CompletionResource(
                status = ResourceStatus.LOADING,
                error = null
            )

        @JvmStatic
        fun error(error: MessageData?) =
            CompletionResource(
                status = ResourceStatus.ERROR,
                error = error
            )

        @JvmStatic
        fun error(message: String) = error(
            error = StringMessageData(message)
        )

        @JvmStatic
        fun error(throwable: Throwable) =
            CompletionResource(
                status = ResourceStatus.ERROR,
                error = throwable.toMessageData()
            )

    }

}