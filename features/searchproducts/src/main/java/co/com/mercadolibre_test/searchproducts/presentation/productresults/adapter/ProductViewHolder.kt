package co.com.mercadolibre_test.searchproducts.presentation.productresults.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.com.mercadolibre_test.core.utils.formatPrice
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.ProductResultItemBinding
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.utils.DISCOUNT_COMPLEMENT
import co.com.mercadolibre_test.searchproducts.utils.loadImageFromUrl

class ProductViewHolder(parent: ViewGroup, val onProductClickListener: OnProductClickListener) : RecyclerView.ViewHolder(
    ProductResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ProductResultItemBinding.bind(itemView)

    fun bind(product: Product) {
        with(binding) {
            ivProductIcon.loadImageFromUrl(product.image)
            tvProductName.text = product.name
            tvProductPrice.text = formatPrice(product.price)
            product.installments?.let {             // Si existen cuotas se muestra el campo con su respectivo valor
                if (product.isWithoutInterest) {    // Este campo se configura el el mapeo del modelo de data al modelo de domain
                    tvProductInstallments.run {
                        text = String.format(context.getString(R.string.installment_label_without_interest, it))
                        setTextColor(ContextCompat.getColor(context, R.color.mercado_libre_green_color))
                    }
                } else {
                    tvProductInstallments.run {
                        text = String.format(context.getString(R.string.installment_label, it))
                    }
                }
            }
            product.discount?.let {                 // Si existe descuentos, se muestra el campo con el respectivo valor. El descuento se calcula en el mapper del modelo de data al modelo de domain
                val discount = "$it$DISCOUNT_COMPLEMENT"
                tvProductDiscount.run {
                    text = discount
                    visibility = View.VISIBLE
                }
            }
            if (product.isFreeShipping) {           // Mostramos si el producto tiene envío gratis
                tvProductShipping.visibility = View.VISIBLE
            }
            root.setOnClickListener {               // Se asigna el listener del item que devuelve el producto en cuestion
                onProductClickListener.onProductClick(product)
            }
        }
    }
}
