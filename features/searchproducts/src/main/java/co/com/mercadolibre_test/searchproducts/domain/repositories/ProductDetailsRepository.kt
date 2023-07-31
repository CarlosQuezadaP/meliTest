package co.com.mercadolibre_test.searchproducts.domain.repositories

import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import kotlinx.coroutines.flow.Flow

interface ProductDetailsRepository {

    fun getProductDescription(productId: String): Flow<Either<ProductDetailsException, String>>
}
