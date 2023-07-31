package co.com.mercadolibre_test.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Installments
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductDescriptionUseCase
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.*
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.ProductDetailsScreenState
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.ProductDetailsViewModel
import co.com.mercadolibre_test.testutils.CoroutineTestRule
import co.com.mercadolibre_test.testutils.relaxedMockk
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ProductDetailsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutinesRule = CoroutineTestRule()
    private val observer = relaxedMockk<Observer<ProductDetailsScreenState>>()
    private val getProductDescriptionUseCase = relaxedMockk<GetProductDescriptionUseCase>()
    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private val product = Product(
        id = "ASJDFHNO234H8HO",
        name = "Apple iPhone 11 (128 Gb) - Morado",
        price = 58900,
        originalPrice = 67999,
        discount = 25,
        image = "https://api.mercadolibre.com/images/img1.jpg",
        installments = Installments(36, 99.98),
        isFreeShipping = true,
        isWithoutInterest = false
    )
    private val descriptionResponse = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A ab, accusantium ad, consequuntur cum dolorum exercitationem facere illum in ipsa iusto maiores nemo nulla provident qui quisquam saepe similique vel!"
    private val mockResponse = flowOf(Either.right(descriptionResponse))

    @Test
    fun `given the ViewModel when it is created then should get a Loading state`() {
        val slots = mutableListOf<ProductDetailsScreenState>()
        productDetailsViewModel = ProductDetailsViewModel(product, getProductDescriptionUseCase)
        productDetailsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }
        Assert.assertTrue(slots.first() is ProductDetailsScreenState.Loading)

        coVerify(exactly = 1) {
            getProductDescriptionUseCase.invoke(product.id)
        }
    }

    @Test
    fun `given product as param when ViewModel is created then should provide a lis of DetailItemView with an specific order`() {
        val screenStateSlots = mutableListOf<ProductDetailsScreenState>()
        val detailsSlots = mutableListOf<List<DetailItemView>>()
        val detailsItemsObserver = relaxedMockk<Observer<List<DetailItemView>>>()
        productDetailsViewModel = ProductDetailsViewModel(product, getProductDescriptionUseCase)
        productDetailsViewModel.state.asLiveData().observeForever(observer)
        productDetailsViewModel.details.observeForever(detailsItemsObserver)

        coVerify { observer.onChanged(capture(screenStateSlots)) }
        Assert.assertTrue(screenStateSlots.first() is ProductDetailsScreenState.Loading)

        runBlocking { delay(500) }
        coVerify { detailsItemsObserver.onChanged(capture(detailsSlots)) }

        // We validate that the order in which the details should be displayed on the screen is not modified
        with(detailsSlots.last() as ArrayList<DetailItemView>) {
            this.size shouldBeEqualTo 4
            this[0] shouldBeInstanceOf DetailTextItem::class
            this[1] shouldBeInstanceOf DetailImageItem::class
            this[2] shouldBeInstanceOf DetailPriceItem::class
            this[3] shouldBeInstanceOf DetailFreeShippingItem::class
        }
    }

    @Test
    fun `given product as param when ViewModel is created then should provide a lis of DetailItemView with an specific order without free shipping section`() {
        val screenStateSlots = mutableListOf<ProductDetailsScreenState>()
        val detailsSlots = mutableListOf<List<DetailItemView>>()
        val detailsItemsObserver = relaxedMockk<Observer<List<DetailItemView>>>()
        productDetailsViewModel = ProductDetailsViewModel(product.copy(isFreeShipping = false), getProductDescriptionUseCase)
        productDetailsViewModel.state.asLiveData().observeForever(observer)
        productDetailsViewModel.details.observeForever(detailsItemsObserver)

        coVerify { observer.onChanged(capture(screenStateSlots)) }
        Assert.assertTrue(screenStateSlots.first() is ProductDetailsScreenState.Loading)

        runBlocking { delay(500) }
        coVerify { detailsItemsObserver.onChanged(capture(detailsSlots)) }

        // We validate that the order in which the details should be displayed on the screen is not modified
        with(detailsSlots.last() as ArrayList<DetailItemView>) {
            this.size shouldBeEqualTo 3
            this[0] shouldBeInstanceOf DetailTextItem::class
            this[1] shouldBeInstanceOf DetailImageItem::class
            this[2] shouldBeInstanceOf DetailPriceItem::class
        }
    }

    @Test
    fun `given product as param when ViewModel is created then should provide a lis of DetailItemView and then should add the description section to the list`() {
        val detailsSlots = mutableListOf<List<DetailItemView>>()
        val detailsItemsObserver = relaxedMockk<Observer<List<DetailItemView>>>()
        coEvery { getProductDescriptionUseCase.invoke(product.id) } answers {mockResponse}
        productDetailsViewModel = ProductDetailsViewModel(product, getProductDescriptionUseCase)
        productDetailsViewModel.details.observeForever(detailsItemsObserver)

        runBlocking { delay(500) }
        coVerify { detailsItemsObserver.onChanged(capture(detailsSlots)) }

        // We validate that the order in which the details should be displayed on the screen is not modified
        with(detailsSlots.last() as ArrayList<DetailItemView>) {
            this.size shouldBeEqualTo 5
            this[0] shouldBeInstanceOf DetailTextItem::class
            this[1] shouldBeInstanceOf DetailImageItem::class
            this[2] shouldBeInstanceOf DetailPriceItem::class
            this[3] shouldBeInstanceOf DetailFreeShippingItem::class
            this[4] shouldBeInstanceOf DetailTextItem::class
        }

        coVerify(exactly = 1) {
            getProductDescriptionUseCase.invoke(product.id)
        }
    }

    @Test
    fun `given product as param when ViewModel is created then should provide a lis of DetailItemView and then should get a ProductDetailsException`() {
        val screenStateSlots = mutableListOf<ProductDetailsScreenState>()
        val detailsSlots = mutableListOf<List<DetailItemView>>()
        val detailsItemsObserver = relaxedMockk<Observer<List<DetailItemView>>>()
        coEvery { getProductDescriptionUseCase.invoke(product.id) } answers {flowOf(Either.left(ProductDetailsException.DescriptionNotAvailable()))}
        productDetailsViewModel = ProductDetailsViewModel(product.copy(isFreeShipping = false), getProductDescriptionUseCase)
        productDetailsViewModel.state.asLiveData().observeForever(observer)
        productDetailsViewModel.details.observeForever(detailsItemsObserver)

        runBlocking { delay(500) }
        coVerify { detailsItemsObserver.onChanged(capture(detailsSlots)) }

        // We validate that the order in which the details should be displayed on the screen is not modified
        with(detailsSlots.last() as ArrayList<DetailItemView>) {
            this.size shouldBeEqualTo 3
            this[0] shouldBeInstanceOf DetailTextItem::class
            this[1] shouldBeInstanceOf DetailImageItem::class
            this[2] shouldBeInstanceOf DetailPriceItem::class
        }

        coVerify { observer.onChanged(capture(screenStateSlots)) }
        with((screenStateSlots.last() as ProductDetailsScreenState.Failure)) {
            this.error shouldBeInstanceOf ProductDetailsException.DescriptionNotAvailable::class
        }

        coVerify(exactly = 1) {
            getProductDescriptionUseCase.invoke(product.id)
        }
    }
}
