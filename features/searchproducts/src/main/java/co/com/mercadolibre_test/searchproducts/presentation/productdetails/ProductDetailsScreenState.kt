package co.com.mercadolibre_test.searchproducts.presentation.productdetails

import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView

sealed class ProductDetailsScreenState {

    object Loading : ProductDetailsScreenState()

    class Success(val details: List<DetailItemView>) : ProductDetailsScreenState()

    class Failure(val error: ProductDetailsException) : ProductDetailsScreenState()
}
