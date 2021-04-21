package ro.dragossusi.livedata.extensions

import android.widget.EditText
import ro.dragossusi.messagedata.MessageData
import ro.dragossusi.messagedata.parser.MessageDataParser

/**
 *
 * @author Dragos
 * @since 09.07.2020
 */
fun EditText.setEditTextError(
    errorData: MessageData?,
    parser: MessageDataParser
) = setEditTextError(errorData?.getMessage(parser), errorData != null)

fun EditText.setEditTextError(
    errorMessage: CharSequence? = null,
    showError: Boolean = errorMessage != null
) {
    if (showError) {
        error = errorMessage
    } else {
        error = null
    }
}