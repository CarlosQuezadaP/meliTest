package co.com.mercadolibre_test.searchproducts.presentation.productresults

import androidx.paging.PagingData
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductResultsException
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import kotlinx.coroutines.flow.Flow

sealed class ProductResultsScreenState {

    object Loading : ProductResultsScreenState()

    class Success(val products: Flow<PagingData<Product>>) : ProductResultsScreenState()

    class Failure(val error: ProductResultsException) : ProductResultsScreenState()
}
