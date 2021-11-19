package ro.dragossusi.resource

import ro.dragossusi.messagedata.MessageData

interface Resource {
    val status: ResourceStatus

    fun requireError(): MessageData
}

val Resource.isSuccessful: Boolean
    get() = status == ResourceStatus.SUCCESS

val Resource.isFailed: Boolean
    get() = status == ResourceStatus.ERROR

val Resource.isLoading: Boolean
    get() = status == ResourceStatus.LOADING

val Resource.isFinished: Boolean
    get() = isFailed || isSuccessful