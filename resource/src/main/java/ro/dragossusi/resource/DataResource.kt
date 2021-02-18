package ro.dragossusi.resource

import androidx.annotation.StringRes
import ro.dragossusi.messagedata.*

/**
 *
 * @author Dragos
 * @since 26.03.2020
 */
open class DataResource<out T> protected constructor(
    status: ResourceStatus,
    val data: T?,
    error: MessageData?
) : CompletionResource(status, error) {

    fun requireData(): T = data ?: throw Exception("Data is null, status: $status")

    fun toCompletion() =
        CompletionResource(
            status,
            error
        )

    companion object {

        @JvmStatic
        fun <T> error(errorData: MessageData) =
            DataResource<T>(
                status = ResourceStatus.ERROR,
                data = null,
                error = errorData
            )

        @JvmStatic
        fun <T> error(
            throwable: Throwable
        ) = error<T>(errorData = ThrowableMessageData(throwable))

        @JvmStatic
        fun <T> error(
            message: String
        ) = error<T>(errorData = StringMessageData(message))

        @JvmStatic
        fun <T> loading() =
            DataResource<T>(
                status = ResourceStatus.LOADING,
                data = null,
                error = null
            )

        @JvmStatic
        fun <T> success(data: T?) =
            DataResource<T>(
                status = ResourceStatus.SUCCESS,
                data = data,
                error = null
            )

    }

}