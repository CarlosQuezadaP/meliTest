package co.com.mercadolibre_test.searchproducts.data.models.productresults

import com.google.gson.annotations.SerializedName

data class ApiShipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean
)
