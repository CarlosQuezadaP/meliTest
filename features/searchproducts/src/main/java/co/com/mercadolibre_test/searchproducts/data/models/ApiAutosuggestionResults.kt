package co.com.mercadolibre_test.searchproducts.data.models

import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion
import com.google.gson.annotations.SerializedName

data class ApiAutosuggestionResults(
    @SerializedName("q")
    val query: String,
    @SerializedName("suggested_queries")
    val suggestions: List<ApiAutosuggestion>
) {

    fun toAutosuggestion() = Autosuggestion(
        query = query,
        suggestions = suggestions.map { it.querySuggested }
    )
}
