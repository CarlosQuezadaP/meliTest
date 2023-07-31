package co.com.mercadolibre_test.searchproducts.domain.models

data class Autosuggestion(
    val query: String,
    val suggestions: List<String>
)
