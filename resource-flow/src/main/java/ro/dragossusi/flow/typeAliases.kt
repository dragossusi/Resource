package ro.dragossusi.flow

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource


/**
 *
 * @since 9/3/20
 * @author dragos
 */
typealias CompletionFlow = Flow<CompletionResource?>
typealias ResourceFlow<T> = Flow<DataResource<T>?>