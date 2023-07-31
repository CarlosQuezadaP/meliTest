package co.com.mercadolibre_test.searchproducts.data.services

import co.com.mercadolibre_test.searchproducts.data.models.autoSuggestionResponse.AutoSuggestionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AutosuggestionsService {

    @GET("sites/MLA/search")
    suspend fun getAutosuggestions(@Query("q") query: String): AutoSuggestionResponse
}
