package co.com.mercadolibre_test.searchproducts.domain.models.productresults

import android.os.Parcelable
import co.com.mercadolibre_test.core.utils.formatPrice
import kotlinx.parcelize.Parcelize

@Parcelize
data class Installments(val quantity: Int, val amount: Double) : Parcelable {

    override fun toString() = "${quantity}x ${formatPrice(amount)}"
}
