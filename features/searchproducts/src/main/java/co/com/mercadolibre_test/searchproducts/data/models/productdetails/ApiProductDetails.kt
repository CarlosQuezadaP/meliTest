package co.com.mercadolibre_test.searchproducts.data.models.productdetails

import com.google.gson.annotations.SerializedName

data class ApiProductDetails(
    @SerializedName("plain_text")
    val description: String
)
