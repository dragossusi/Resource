package ro.dragossusi.resource.flow.observer

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.sevens.android.livedata.observer.ResourceObserver

//import ro.dragossusi.observer.CompletionResourceObserver
//import ro.dragossusi.observer.DataResourceObserver

//fun <T> dataObserver(
//    errorHandler: MessageDataHandler?,
//    onFinished: ((Boolean) -> Unit)? = null,
//    onSuccess: (T?) -> Unit
//) = lazy<DataResourceObserver<T>> {
//    object : DataResourceObserver<T>(errorHandler) {
//        override fun onSuccess(data: T?) {
//            super.onSuccess(data)
//            onSuccess.invoke(data)
//        }
//
//        override fun onFinished(success: Boolean) {
//            super.onFinished(success)
//            onFinished?.invoke(success)
//        }
//    }
//}
//
//fun completionObserver(
//    errorHandler: MessageDataHandler?,
//    onFinished: ((Boolean) -> Unit)? = null,
//    onCompleted: () -> Unit
//) = lazy<CompletionResourceObserver> {
//    object : CompletionResourceObserver(errorHandler) {
//        override fun onCompleted() {
//            super.onCompleted()
//            onCompleted.invoke()
//        }
//
//        override fun onFinished(success: Boolean) {
//            super.onFinished(success)
//            onFinished?.invoke(success)
//        }
//    }
//}

fun <T> dataObserver(
    errorHandler: MessageDataHandler?,
    setup: DataResourceObserver<T>.() -> Unit
) = DataResourceObserver(errorHandler, setup)

fun completionObserver(
    errorHandler: MessageDataHandler?,
    setup: CompletionResourceObserver.() -> Unit
) = CompletionResourceObserver(errorHandler, setup)