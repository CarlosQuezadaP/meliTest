package co.com.mercadolibre_test.searchproducts.data.services

import co.com.mercadolibre_test.searchproducts.data.models.productresults.ApiProductsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {

    @GET("sites/MLA/search")
    suspend fun fetchProducts(
        @Query("q") query: String,
        @Query("offset") offset: Int
    ): ApiProductsResult
}
