package co.com.mercadolibre_test.searchproducts.domain.repositories

import androidx.paging.Pager
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product

interface ProductsRepository {

    fun fetchProducts(query: String): Pager<Int, Product>
}
