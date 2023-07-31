package co.com.mercadolibre_test.searchproducts.presentation.productresults

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * Para que el factory se pueda usar con la extension de kotlin by viewModels es necesario crear esta interface y anotarla con @AssistedFactory
 *
 * El proposito de esta interface es proveer el verdadero Factory que extienda a AbstractSavedStateViewModelFactory y que reciba los parametros asistidos
 */
@AssistedFactory
interface ProductResultsViewModelAssistedFactory {

    fun create(owner: SavedStateRegistryOwner, query: String): ProductResultsViewModelFactory
}

class ProductResultsViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val query: String,
    private val getProductsUseCase: GetProductsUseCase,
    private val errorHandler: ErrorHandler
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return ProductResultsViewModel(query, getProductsUseCase, errorHandler) as T
    }
}
