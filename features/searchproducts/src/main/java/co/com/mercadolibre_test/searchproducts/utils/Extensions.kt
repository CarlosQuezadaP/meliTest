package co.com.mercadolibre_test.searchproducts.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.com.mercadolibre_test.searchproducts.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

/**
 * Esta extensión sirve para detectar que el usuario ha presionado el botón de busqueda en el teclado. Si el usuario presiona en ese botón y la búsqueda
 * no está vacia, se llama la función onSearchClick
 */
fun TextInputEditText.addSearchActionListener(onSearchClick: (String) -> Unit) {
    setOnEditorActionListener { textView, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val query = textView.text.toString()
            if (query.isNotEmpty()) {
                onSearchClick(query)
                true
            } else {
                false
            }
        } else {
            false
        }
    }
}

/**
 * Permite abrir el teclado cuando se llama en un fragmento
 */
fun Fragment.showKeyBoard(view: View) {
    val imn = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imn.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Con esta extensión mandamos a cargar una imagen con la url en un ImageView
 *
 * se cambia el protocolo http por https para evitar errores en la librería de picasso
 */
fun ImageView.loadImageFromUrl(url: String) {
    Picasso.with(context)
        .load(url.replace("http:", "https://")).fit().centerCrop()
        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_more_horiz_24))
        .error(ContextCompat.getDrawable(context, R.drawable.ic_clear))
        .into(this)
}

/**
 * Con esta extensión iniciamos el efecto de un shimer especificado en alguna vista y modificamos su visibilidad
 */
fun ShimmerFrameLayout.show() {
    startShimmer()
    visibility = View.VISIBLE
}

/**
 * Con esta extensión detenemos el efecto de un shimer especificado en alguna vista y modificamos su visibilidad
 */
fun ShimmerFrameLayout.hide() {
    stopShimmer()
    visibility = View.GONE
}
