package ro.dragossusi.resource

fun interface OnSuccessListener<T> {
    fun onSuccess(data: T?)
}