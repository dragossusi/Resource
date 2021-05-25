package ro.dragossusi.resource

import ro.dragossusi.messagedata.MessageData

fun interface OnFailureListener {
    fun onFailure(error: MessageData?)
}