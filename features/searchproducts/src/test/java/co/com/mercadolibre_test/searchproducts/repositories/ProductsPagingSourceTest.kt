package co.com.mercadolibre_test.searchproducts.repositories

import androidx.paging.PagingSource
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.searchproducts.data.models.productresults.*
import co.com.mercadolibre_test.searchproducts.data.services.ProductsPagingSource
import co.com.mercadolibre_test.searchproducts.data.services.ProductsService
import co.com.mercadolibre_test.testutils.relaxedMockk
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ProductsPagingSourceTest {

    private val productsService = relaxedMockk<ProductsService>()
    private val mockQuery = "iphone"
    private val productsResult = ApiProductsResult(
        paging = ApiPaging(50, 1000),
        results = listOf(
            ApiProduct(
                "ASJDFHNO234H8HO",
                "Apple iPhone 11 (128 Gb) - Morado",
                58900,
                67999,
                "https://api.mercadolibre.com/images/img1.jpg",
                ApiInstallments(36, 99.98),
                ApiShipping(true),
                ApiDifferentialPricing("ASUYX71263")
            ),
            ApiProduct(
                "MASODJCN892Y343",
                "Apple iPhone 12 Pro Max (256 Gb) - Grafito",
                12350,
                null,
                "https://api.mercadolibre.com/images/img2.jpg",
                ApiInstallments(12, 5.98),
                ApiShipping(false),
                ApiDifferentialPricing("SUY63X712")
            ),
            ApiProduct(
                "XAYPFOIQHWELN28",
                "Apple iPhone 12 (128 Gb) - (product)(red)",
                58900,
                null,
                "https://api.mercadolibre.com/images/img3.jpg",
                ApiInstallments(24, 56.98),
                ApiShipping(true),
                null
            )
        )
    )

    @Test
    fun `given PagingSource when load is called then should get a list of products with nextKey=100`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) }.answers { productsResult }

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = productsResult.results.map { it.toProduct() },
                    prevKey = null,
                    nextKey = 100
                ),
                pagingSource.load(PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                ))
            )
        }
    }

    @Test
    fun `given PagingSource when load is called by second time then should get a list of products with nextKey=150`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) }.answers { productsResult }
        coEvery { productsService.fetchProducts(mockQuery, 100) }.answers { productsResult.copy(paging = ApiPaging(100, 1000)) }

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = productsResult.results.map { it.toProduct() },
                    prevKey = null,
                    nextKey = 100
                ),
                pagingSource.load(PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                ))
            )
            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = productsResult.results.map { it.toProduct() },
                    prevKey = null,
                    nextKey = 150
                ),
                pagingSource.load(PagingSource.LoadParams.Refresh(
                    key = 100,
                    loadSize = 0,
                    placeholdersEnabled = false
                ))
            )
        }
    }

    @Test
    fun `given PagingSource when load is called and total is 0 then should get an empty list of products with nextKey=null`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) }.answers { productsResult.copy(results = listOf(), paging = ApiPaging(50, 0)) }

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = listOf(),
                    prevKey = null,
                    nextKey = null
                ),
                pagingSource.load(PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                ))
            )
        }
    }

    @Test
    fun `given PagingSource when load is called then should get an error of type NetworkException-NotFound`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) } throws NetworkException.NotFound("asdasd")

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            val pagingSourceResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                )
            )

            Assert.assertTrue(pagingSourceResult is PagingSource.LoadResult.Error)
            Assert.assertTrue((pagingSourceResult as PagingSource.LoadResult.Error).throwable is NetworkException.NotFound)
        }
    }

    @Test
    fun `given PagingSource when load is called then should get an error of type NetworkException-ServerError`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) } throws NetworkException.ServerError("asdasd")

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            val pagingSourceResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                )
            )

            Assert.assertTrue(pagingSourceResult is PagingSource.LoadResult.Error)
            Assert.assertTrue((pagingSourceResult as PagingSource.LoadResult.Error).throwable is NetworkException.ServerError)
        }
    }

    @Test
    fun `given PagingSource when load is called then should get an error of type HttpException`() {
        coEvery { productsService.fetchProducts(mockQuery, 50) } throws HttpException(Response.success(ResponseBody.create(MediaType.get("application/json"), "expected object but was and string")))

        runBlocking {
            val pagingSource = ProductsPagingSource(productsService, mockQuery)

            val pagingSourceResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 50,
                    loadSize = 0,
                    placeholdersEnabled = false
                )
            )

            Assert.assertTrue(pagingSourceResult is PagingSource.LoadResult.Error)
            Assert.assertTrue((pagingSourceResult as PagingSource.LoadResult.Error).throwable is HttpException)
        }
    }
}
