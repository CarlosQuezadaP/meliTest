package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Es clase es un genérico que representa la estructura general que debe de tener todos los viewHolder que se mostrarán en el adaptador ProductDetailsadapter
 */
abstract class BaseProductDetailsViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: T)
}
