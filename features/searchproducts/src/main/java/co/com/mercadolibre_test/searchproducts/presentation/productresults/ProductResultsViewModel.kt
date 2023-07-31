package co.com.mercadolibre_test.searchproducts.presentation.productresults

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import co.com.mercadolibre_test.core.base.BaseViewModel
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductResultsException
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductsUseCase
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductResultsViewModel @AssistedInject constructor(
    private val query: String,
    private val getProductsUseCase: GetProductsUseCase,
    private val errorHandler: ErrorHandler
) : BaseViewModel<ProductResultsScreenState>(ProductResultsScreenState.Loading) {

    /**
     * Cuando se crea la vista se manda a llamar inmediatamente al caso de uso para devolver el paginData que se encargará de solicitar y actualizar los datos del adaptador
     *
     * los datos del pagin se cachean en el scope del viewmodel para que se conserven los datos cuando la pantalla sufre un cambio de configuración
     */
    init {
        viewModelScope.launch {
            val pagerData = withContext(Dispatchers.IO) {
                getProductsUseCase(query)
            }.flow.cachedIn(viewModelScope)
            mutableState.value = ProductResultsScreenState.Success(pagerData)
        }
    }

    // Dada la configuración de pagin, fue necesario mover el errorHandler para este viewModel. Este método se llama desde la vista cuando el stado del adaptador es error
    fun pagingException(exception: Throwable) {
        mutableState.value = if (exception is NetworkException) {
            ProductResultsScreenState.Failure(ProductResultsException.ProductsNotAvailable())
        } else {
            ProductResultsScreenState.Failure(ProductResultsException.UnknownError(errorHandler.getError(exception)))
        }
    }
}
