package co.com.mercadolibre_test.searchproducts.data.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import retrofit2.HttpException
import java.io.IOException

class ProductsPagingSource(
    private val productsService: ProductsService,
    private val query: String,
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val nextPage = params.key ?: DEFAULT_PAGE_OFFSET                // El offset por defecto es 50 ya que con este valor, el servicio response con los primeros 50 elementos

        return try {
            val response = productsService.fetchProducts(query, nextPage)
            LoadResult.Page(
                data = response.results.map { it.toProduct() },
                prevKey = null,                                         // Sólo se hacen consultas de nuevos datos cuando se hace scroll hacia abajo (navegación sólo hacia adelante)
                nextKey = if (response.paging.total == 0) null else response.paging.offset + PAGE_SIZE // El el resultado de pagin es 0 significa que no se obtuvieron resultados para la consulta
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }
}

private const val DEFAULT_PAGE_OFFSET = 0
private const val PAGE_SIZE = 50
