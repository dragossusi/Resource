package ro.dragossusi.observer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ro.dragossusi.livedata.ObservableLiveData
import ro.dragossusi.messagedata.handler.MessageDataHandler
import ro.dragossusi.resource.CompletionResource

fun <T> dataObserver(
    errorHandler: MessageDataHandler?
) = DataResourceObserver<T>(errorHandler)

fun completionObserver(
    errorHandler: MessageDataHandler?,
) = CompletionResourceObserver(errorHandler)

fun <R : CompletionResource> ObservableLiveData<out R?>.observeOnce(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<R?>
) {
    observe(lifecycleOwner) {
        if (it == null) return@observe
        observer.onChanged(it)
        if (it.isFinished) {
            clear()
        }
    }
}