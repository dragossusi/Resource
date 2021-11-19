package ro.dragossusi.resource

import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.StringMessageData
import ro.dragossusi.messagedata.toMessageData
import kotlin.jvm.JvmStatic

/**
 *
 * @author Dragos
 * @since 26.03.2020
 */
sealed class DataResource<out T> constructor(
    override val status: ResourceStatus,
) : Resource {

    fun requireData(): T {
        if (this !is Success) throw IllegalStateException("The resource has status $status")
        return data
    }

    override fun requireError(): MessageData {
        if (this !is Error) {
            throw IllegalStateException("The resource has status $status")
        }
        return error ?: throw Exception("Error null, status: $status")
    }

    fun toCompletion(): CompletionResource = when (this) {
        is Loading<T> -> CompletionResource.Loading
        is Success<T> -> CompletionResource.Completed
        is Error<T> -> CompletionResource.Completed
    }

    class Loading<out T> : DataResource<T>(
        status = ResourceStatus.LOADING,
    )

    data class Success<out T>(
        val data: T
    ) : DataResource<T>(
        status = ResourceStatus.SUCCESS,
    )

    data class Error<out T>(
        val error: MessageData?
    ) : DataResource<T>(
        status = ResourceStatus.ERROR,
    )

    companion object {

        @JvmStatic
        fun <T> Error(
            throwable: Throwable
        ) = Error<T>(
            error = throwable.toMessageData()
        )

        @JvmStatic
        fun <T> Error(
            message: String
        ) = Error<T>(error = StringMessageData(message))

    }

}