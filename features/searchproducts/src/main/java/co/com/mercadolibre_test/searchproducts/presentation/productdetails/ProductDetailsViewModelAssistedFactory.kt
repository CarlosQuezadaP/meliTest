package co.com.mercadolibre_test.searchproducts.presentation.productdetails

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductDescriptionUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * Para que el factory se pueda usar con la extension de kotlin by viewModels es necesario crear esta interface y anotarla con @AssistedFactory
 *
 * El proposito de esta interface es proveer el verdadero Factory que extienda a AbstractSavedStateViewModelFactory y que reciba los parametros asistidos
 */
@AssistedFactory
interface ProductDetailsViewModelAssistedFactory {

    fun create(owner: SavedStateRegistryOwner, product: Product): ProductDetailsViewModelFactory
}

class ProductDetailsViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val product: Product,
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return ProductDetailsViewModel(product, getProductDescriptionUseCase) as T
    }
}
