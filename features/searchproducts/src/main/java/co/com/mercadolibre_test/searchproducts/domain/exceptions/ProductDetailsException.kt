package co.com.mercadolibre_test.searchproducts.domain.exceptions

import androidx.annotation.StringRes
import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.searchproducts.R

sealed class ProductDetailsException : ErrorEntity() {

    class DescriptionNotAvailable(@StringRes val errorMessage: Int = R.string.product_description_empty) : ProductDetailsException()

    class UnknownError(val error: ErrorEntity) : ProductDetailsException()
}
