package co.com.mercadolibre_test.searchproducts.data.services

import co.com.mercadolibre_test.searchproducts.data.models.productdetails.ApiProductDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsService {

    @GET("items/{id}/description")
    suspend fun getProductDescription(@Path("id") productId: String): ApiProductDetails
}
