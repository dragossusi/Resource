package ro.dragossusi.livedata

import androidx.lifecycle.LiveData
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource


/**
 *
 * @since 9/3/20
 * @author dragos
 */
typealias CompletionLiveData = LiveData<CompletionResource?>
typealias ResourceLiveData<T> = LiveData<DataResource<T>?>