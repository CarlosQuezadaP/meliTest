package co.com.mercadolibre_test.searchproducts.domain.usecases

import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetProductDescriptionUseCase(
    private val repository: ProductDetailsRepository
) {

    operator fun invoke(productId: String): Flow<Either<ProductDetailsException, String>> {
        return repository.getProductDescription(productId)
    }
}
