package co.com.mercadolibre_test.searchproducts.data.models.productresults

import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Installments
import com.google.gson.annotations.SerializedName

data class ApiInstallments(
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("amount")
    val amount: Double
) {

    fun toInstallments() = Installments(quantity, amount)
}
