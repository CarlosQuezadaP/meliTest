package co.com.mercadolibre_test.searchproducts.data.models.productresults

import com.google.gson.annotations.SerializedName


data class ApiPaging(                   // El objetivo de esta clase es controlar el paginado en la vista de productos
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("total")
    val total: Int
)
