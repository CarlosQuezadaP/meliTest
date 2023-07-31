package co.com.mercadolibre_test.searchproducts.data.models.autoSuggestionResponse

data class AutoSuggestionResponse(
    val query: String,
    val results: List<Result>,
)