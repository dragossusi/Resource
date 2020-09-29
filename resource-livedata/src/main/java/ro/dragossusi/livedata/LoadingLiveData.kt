package ro.dragossusi.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import ro.dragossusi.resource.CompletionResource

/**
 *
 * @author Dragos
 * @since 07.08.2020
 */
class LoadingLiveData(
    vararg liveData: LiveData<out CompletionResource?>
) : MediatorLiveData<Boolean>() {

    val observer = Observer<CompletionResource?> {
        value = check()
    }

    private val liveDatas = mutableListOf<LiveData<out CompletionResource>>(
        *liveData
    )

    init {
        liveDatas.forEach {
            addSource(it, observer)
        }
    }

    fun remove(liveData: LiveData<out CompletionResource?>): Boolean {
        val response = liveDatas.remove(liveData)
        if (response) removeSource(liveData)
        return response
    }

    fun add(liveData: LiveData<out CompletionResource?>): Boolean {
        val response = liveDatas.add(liveData)
        if (response) addSource(liveData, observer)
        return response
    }

    operator fun minusAssign(liveData: LiveData<out CompletionResource?>) {
        remove(liveData)
    }

    operator fun plusAssign(liveData: LiveData<out CompletionResource?>) {
        add(liveData)
    }

    private fun check(): Boolean {
        return liveDatas.any {
            it.value?.isLoading == true
        }
    }

}