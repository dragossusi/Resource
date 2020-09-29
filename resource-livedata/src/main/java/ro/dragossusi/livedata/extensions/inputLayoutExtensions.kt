package ro.dragossusi.livedata.extensions

import android.widget.EditText
import ro.rachieru.dragos.errordata.ErrorData

/**
 *
 * @author Dragos
 * @since 09.07.2020
 */
fun EditText.setEditTextError(
    errorData: ErrorData?
) = setEditTextError(errorData?.getError(resources), errorData != null)

fun EditText.setEditTextError(
    errorMessage: String? = null,
    showError: Boolean = errorMessage != null
) {
    if (showError) {
        error = errorMessage
    } else {
        error = null
    }
}