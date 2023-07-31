package co.com.mercadolibre_test.searchproducts.data.models

import com.google.gson.annotations.SerializedName

data class ApiAutosuggestion(
    @SerializedName("q")
    val querySuggested: String
)
