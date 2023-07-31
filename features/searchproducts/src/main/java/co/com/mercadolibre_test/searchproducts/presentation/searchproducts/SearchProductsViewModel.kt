package co.com.mercadolibre_test.searchproducts.presentation.searchproducts

import androidx.lifecycle.viewModelScope
import co.com.mercadolibre_test.core.base.BaseViewModel
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetAutosuggestionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchProductsViewModel @Inject constructor(
    private val getAutosuggestionsUseCase: GetAutosuggestionsUseCase
) : BaseViewModel<SearchProductsScreenState>(SearchProductsScreenState.Nothing) {

    /**
     * Este método se llama cada vez que se escribe en la barra de búsqueda
     *
     * Devuelve un objeto Autosuggestions que contiene una lista de string con las sugerencias de búsqueda
     */
    fun search(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAutosuggestionsUseCase(query)
            }.collect {
                it.fold({ error ->
                    mutableState.value = SearchProductsScreenState.Failure(error)
                }, { autosuggestionsResponse ->
                    mutableState.value = SearchProductsScreenState.Success(autosuggestionsResponse)
                })
            }
        }
    }
}
