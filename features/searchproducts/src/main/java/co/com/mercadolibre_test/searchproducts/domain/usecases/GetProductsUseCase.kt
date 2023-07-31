package co.com.mercadolibre_test.searchproducts.domain.usecases

import androidx.paging.Pager
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductsRepository

class GetProductsUseCase(
    private val repository: ProductsRepository
) {

    operator fun invoke(query: String): Pager<Int, Product> {
        return repository.fetchProducts(query)
    }
}
