package co.com.mercadolibre_test.searchproducts.data.models.productresults

import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import com.google.gson.annotations.SerializedName

data class ApiProduct(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("original_price")
    val originalPrice: Double?,
    @SerializedName("thumbnail")
    val image: String,
    @SerializedName("installments")
    val installments: ApiInstallments?,
    @SerializedName("shipping")
    val shipping: ApiShipping,
    @SerializedName("differential_pricing")
    val differentialPricing: ApiDifferentialPricing?
) {

    fun toProduct() = Product(
        id,
        name,
        price,
        originalPrice,
        calculateDiscount(),
        image,
        installments?.toInstallments(),
        shipping.freeShipping,
        differentialPricing != null
    )

    private fun calculateDiscount(): Double? {
        val originalPrice = originalPrice ?: return null
        val freeValue = originalPrice - price
        if (freeValue <= 0) return null
        return (freeValue * 100) / originalPrice
    }
}
