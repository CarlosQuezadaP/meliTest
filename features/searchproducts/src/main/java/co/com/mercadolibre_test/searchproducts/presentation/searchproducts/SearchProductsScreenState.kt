package co.com.mercadolibre_test.searchproducts.presentation.searchproducts

import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion

sealed class SearchProductsScreenState {

    object Nothing : SearchProductsScreenState()

    class Success(val autosuggestion: Autosuggestion) : SearchProductsScreenState()

    class Failure(val error: AutosuggestionsException) : SearchProductsScreenState()
}
