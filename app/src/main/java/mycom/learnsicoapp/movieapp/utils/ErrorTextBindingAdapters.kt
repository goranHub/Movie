package mycom.learnsicoapp.movieapp.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

/**
 * @author ll4
 * @date 1/26/2021
 */
object ErrorTextBindingAdapters {
    @JvmStatic
    @BindingAdapter("errorText")
    fun errorText(view: TextInputLayout, errorMessage: String) {
        view.error = errorMessage
    }
}