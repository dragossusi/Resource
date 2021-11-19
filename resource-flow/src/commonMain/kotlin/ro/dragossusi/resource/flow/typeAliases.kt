package ro.dragossusi.resource.flow

import kotlinx.coroutines.flow.Flow
import ro.dragossusi.resource.CompletionResource
import ro.dragossusi.resource.DataResource
import ro.dragossusi.resource.Resource


/**
 *
 * @since 9/3/20
 * @author dragos
 */
typealias ResourceFlow = Flow<Resource>
typealias CompletionFlow = Flow<CompletionResource>
typealias DataFlow<T> = Flow<DataResource<T>>