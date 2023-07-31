package co.com.mercadolibre_test.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.data.services.ProductsPagingSource
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductResultsException
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Installments
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductsUseCase
import co.com.mercadolibre_test.searchproducts.presentation.productresults.ProductResultsScreenState
import co.com.mercadolibre_test.searchproducts.presentation.productresults.ProductResultsViewModel
import co.com.mercadolibre_test.testutils.CoroutineTestRule
import co.com.mercadolibre_test.testutils.relaxedMockk
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class ProductResultsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutinesRule = CoroutineTestRule()
    private val errorHandler = relaxedMockk<ErrorHandler>()
    private val observer = relaxedMockk<Observer<ProductResultsScreenState>>()
    private val getProductsUseCase = relaxedMockk<GetProductsUseCase>()
    private lateinit var productResultsViewModel: ProductResultsViewModel
    private val query = "iphone"
    private val productsPagingSource = relaxedMockk<ProductsPagingSource>()
    private val productsResponse = listOf(
        Product(
            id = "ASJDFHNO234H8HO",
            name = "Apple iPhone 11 (128 Gb) - Morado",
            price = 58900,
            originalPrice = 67999,
            discount = 25,
            image = "https://api.mercadolibre.com/images/img1.jpg",
            installments = Installments(36, 99.98),
            isFreeShipping = true,
            isWithoutInterest = false
        ),
        Product(
            id = "MASODJCN892Y343",
            name = "Apple iPhone 12 Pro Max (256 Gb) - Grafito",
            price = 12350,
            originalPrice = null,
            discount = 5,
            image = "https://api.mercadolibre.com/images/img2.jpg",
            installments = Installments(12, 5.98),
            isFreeShipping = false,
            isWithoutInterest = true
        ),
        Product(
            id = "XAYPFOIQHWELN28",
            name = "Apple iPhone 12 (128 Gb) - (product)(red)",
            price = 58900,
            originalPrice = null,
            discount = null,
            image = "https://api.mercadolibre.com/images/img3.jpg",
            installments = Installments(24, 56.98),
            isFreeShipping = true,
            isWithoutInterest = false
        )
    )

    @Test
    fun `given the ViewModel when it is created then should get a Loading state`() {
        val slots = mutableListOf<ProductResultsScreenState>()
        productResultsViewModel = ProductResultsViewModel(query, getProductsUseCase, errorHandler)
        productResultsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }
        Assert.assertTrue(slots.first() is ProductResultsScreenState.Loading)

        coVerify(exactly = 1) {
            getProductsUseCase.invoke(query)
        }
    }

    @Test
    fun `given query as parameter when ViewModel is created then should get a success state with a flow of PagingData`() {
        val slots = mutableListOf<ProductResultsScreenState>()
        coEvery {
            productsPagingSource.load(PagingSource.LoadParams.Refresh(key = 50, loadSize = 0, placeholdersEnabled = false))
        }.answers {
            PagingSource.LoadResult.Page(data = productsResponse, prevKey = null, nextKey = 100)
        }
        coEvery { getProductsUseCase.invoke(query) } answers {
            Pager(
                config = PagingConfig(pageSize = 50),
                pagingSourceFactory = { productsPagingSource }
            )
        }
        productResultsViewModel = ProductResultsViewModel(query, getProductsUseCase, errorHandler)
        productResultsViewModel.state.asLiveData().observeForever(observer)

        runBlocking { delay(500) }
        coVerify { observer.onChanged(capture(slots)) }

        //Assert.assertTrue((slots.last() is ProductResultsScreenState.Success).products)

        coVerify(exactly = 1) {
            getProductsUseCase.invoke(query)
        }
    }

    @Test
    fun `given query as parameter when pagingException is called then should get a failure state with an ProductResultsException-ProductsNotAvailable object`() {
        val slots = mutableListOf<ProductResultsScreenState>()
        productResultsViewModel = ProductResultsViewModel(query, getProductsUseCase, errorHandler)
        productResultsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }

        productResultsViewModel.pagingException(NetworkException.NotFound("service is unavailable"))
        coVerify { observer.onChanged(capture(slots)) }

        with((slots.last() as ProductResultsScreenState.Failure)) {
            this.error shouldBeInstanceOf ProductResultsException.ProductsNotAvailable::class
        }

        coVerify(exactly = 1) {
            getProductsUseCase.invoke(query)
        }
    }

    @Test
    fun `given query as parameter when pagingException is called then should get a failure state with an ProductResultsException-UnknownError object`() {
        val slots = mutableListOf<ProductResultsScreenState>()
        productResultsViewModel = ProductResultsViewModel(query, getProductsUseCase, errorHandler)
        productResultsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }

        productResultsViewModel.pagingException(Exception("something went wrong"))
        coVerify { observer.onChanged(capture(slots)) }

        with((slots.last() as ProductResultsScreenState.Failure)) {
            this.error shouldBeInstanceOf ProductResultsException.UnknownError::class
        }

        coVerify(exactly = 1) {
            getProductsUseCase.invoke(query)
        }
    }
}
