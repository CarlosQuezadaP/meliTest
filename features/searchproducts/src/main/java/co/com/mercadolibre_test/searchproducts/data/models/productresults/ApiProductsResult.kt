package co.com.mercadolibre_test.searchproducts.data.models.productresults

import com.google.gson.annotations.SerializedName


data class ApiProductsResult(
    @SerializedName("paging")
    val paging: ApiPaging,
    @SerializedName("results")
    val results: List<ApiProduct>
)
