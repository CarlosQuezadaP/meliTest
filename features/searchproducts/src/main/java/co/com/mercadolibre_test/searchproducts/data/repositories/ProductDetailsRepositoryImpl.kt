package co.com.mercadolibre_test.searchproducts.data.repositories

import arrow.core.Either
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.core.utils.reportError
import co.com.mercadolibre_test.searchproducts.data.services.ProductDetailsService
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductDetailsRepository
import kotlinx.coroutines.flow.flow

class ProductDetailsRepositoryImpl(
    private val service: ProductDetailsService,
    private val errorHandler: ErrorHandler
) : ProductDetailsRepository {

    /**
     * Either es un objeto de la libería arror que permite encapsular dos tipos de respuesta en su contenido. se devuelve un left cuando ocurre algún
     * error y se devuelve right cuando el proceso es exitoso.
     */
    override fun getProductDescription(productId: String) = flow {
        emit(
            try {
                Either.right(service.getProductDescription(productId).description)
            } catch (exception: Exception) {
                exception.reportError()
                Either.left(
                    if (exception is NetworkException) {
                        ProductDetailsException.DescriptionNotAvailable()
                    } else {
                        ProductDetailsException.UnknownError(errorHandler.getError(exception))
                    }
                )
            }
        )
    }
}
