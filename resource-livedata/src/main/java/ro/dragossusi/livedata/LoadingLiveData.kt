package ro.dragossusi.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import ro.dragossusi.resource.Resource
import ro.dragossusi.resource.isLoading

/**
 *
 * @author Dragos
 * @since 07.08.2020
 */
class LoadingLiveData(
    vararg liveData: LiveData<out Resource?>
) : MediatorLiveData<Boolean>() {

    val observer = Observer<Resource?> {
        value = check()
    }

    private val liveDatas = mutableListOf<LiveData<out Resource>>(
        *liveData
    )

    init {
        liveDatas.forEach {
            addSource(it, observer)
        }
    }

    fun remove(liveData: LiveData<out Resource?>): Boolean {
        val response = liveDatas.remove(liveData)
        if (response) removeSource(liveData)
        return response
    }

    fun add(liveData: LiveData<out Resource?>): Boolean {
        val response = liveDatas.add(liveData)
        if (response) addSource(liveData, observer)
        return response
    }

    operator fun minusAssign(liveData: LiveData<out Resource?>) {
        remove(liveData)
    }

    operator fun plusAssign(liveData: LiveData<out Resource?>) {
        add(liveData)
    }

    private fun check(): Boolean {
        return liveDatas.any {
            it.value?.isLoading == true
        }
    }

}