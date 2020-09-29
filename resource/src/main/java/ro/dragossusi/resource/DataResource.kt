package ro.dragossusi.resource

import androidx.annotation.StringRes
import ro.rachieru.dragos.errordata.ErrorData
import ro.rachieru.dragos.errordata.StringErrorData
import ro.rachieru.dragos.errordata.StringResErrorData
import ro.rachieru.dragos.errordata.ThrowableErrorData

/**
 *
 * @author Dragos
 * @since 26.03.2020
 */
open class DataResource<out T> protected constructor(
    status: ResourceStatus,
    val data: T?,
    error: ErrorData?
) : CompletionResource(status, error) {

    fun requireData(): T = data ?: throw Exception("Data is null, status: $status")

    fun toCompletion() =
        CompletionResource(
            status,
            error
        )

    companion object {

        @JvmStatic
        fun <T> error(errorData: ErrorData) =
            DataResource<T>(
                status = ResourceStatus.ERROR,
                data = null,
                error = errorData
            )

        @JvmStatic
        fun <T> error(
            throwable: Throwable
        ) = error<T>(errorData = ThrowableErrorData(throwable))

        @JvmStatic
        fun <T> error(
            message: String
        ) = error<T>(errorData = StringErrorData(message))

        @JvmStatic
        fun <T> error(
            @StringRes stringCode: Int
        ) = error<T>(errorData = StringResErrorData(stringCode))

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