package co.com.mercadolibre_test.searchproducts.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import co.com.mercadolibre_test.searchproducts.data.services.ProductsPagingSource
import co.com.mercadolibre_test.searchproducts.data.services.ProductsService
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductsRepository

class ProductsRepositoryImpl(
    private val service: ProductsService
) : ProductsRepository {

    /**
     * Para este repositorio no se devuelve un Either, sino, de devuelve el objeto Pager que contiene la información del source para hacer la consulta
     * de más productos cuando se haga scroll en el recyclerview de la pantalla. El errorHandler, para este caso, se movió a el ViewModel.
     *
     * Se indica un pageSize de 50 para que el pager sepa que cuando ha mostrado 50 items debe de solicitar más elementos al servidor
     */
    override fun fetchProducts(query: String) = Pager(
        config = PagingConfig(pageSize = 50),
        pagingSourceFactory = { ProductsPagingSource(service, query) }
    )
}
