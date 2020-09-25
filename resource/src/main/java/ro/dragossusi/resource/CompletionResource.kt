package ro.dragossusi.resource

import ro.rachieru.dragos.errordata.ErrorData
import ro.rachieru.dragos.errordata.StringErrorData
import ro.rachieru.dragos.errordata.ThrowableErrorData

/**
 *
 * @author Dragos
 * @since 26.06.2020
 */
open class CompletionResource(
    val status: ResourceStatus,
    val error: ErrorData?
) {

    val isSuccessful: Boolean
        get() = status == ResourceStatus.SUCCESS

    val isFailed: Boolean
        get() = status == ResourceStatus.ERROR

    val isLoading: Boolean
        get() = status == ResourceStatus.LOADING

    fun requireError(): ErrorData = error ?: throw Exception("Error null, status: $status")

    inline fun <T : CompletionResource> mapTo(block: (CompletionResource) -> T): T {
        return block(this)
    }

    inline fun onSuccess(block: () -> Unit) {
        if (isSuccessful) block()
    }

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
        fun error(error: ErrorData?) =
            CompletionResource(
                status = ResourceStatus.ERROR,
                error = error
            )

        @JvmStatic
        fun error(message: String) = error(
            error = StringErrorData(message)
        )

        @JvmStatic
        fun error(throwable: Throwable) =
            CompletionResource(
                status = ResourceStatus.ERROR,
                error = ThrowableErrorData(throwable)
            )

    }

}