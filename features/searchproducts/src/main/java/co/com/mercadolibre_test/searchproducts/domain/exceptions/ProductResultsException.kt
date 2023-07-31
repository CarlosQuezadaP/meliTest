package co.com.mercadolibre_test.searchproducts.domain.exceptions

import androidx.annotation.StringRes
import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.searchproducts.R

sealed class ProductResultsException : ErrorEntity() {

    class ProductsNotAvailable(@StringRes val errorMessage: Int = R.string.products_not_available) : ProductResultsException()

    class UnknownError(val error: ErrorEntity) : ProductResultsException()
}
