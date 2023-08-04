package co.com.mercadolibre_test.core.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import co.com.mercadolibre_test.core.base.delegates.FragmentViewBindingDelegate

import java.text.NumberFormat

/**
 * Esta extensión se llama en los fragment para obtener los respectivos ViewBinding y evitar el código boilerplate en todos lados
 *
 * la clase FragmentViewBindingDelegate es una implementación tipica para este caso
 */
fun <viewBinding : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> viewBinding) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

/**
 * Permite formatear un numero Long (ej: 98956) en un numero con puntos decimales. Ej: 98.956
 */
fun formatPrice(price: Long): String {
    val format = NumberFormat.getCurrencyInstance().apply { maximumFractionDigits = 0 }
    return format.format(price).replace(",", ".")
}

fun formatPrice(price: Double): String {
    val format = NumberFormat.getCurrencyInstance().apply { maximumFractionDigits = 0 }
    return format.format(price).replace(",", ".")
}
