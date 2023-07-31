package co.com.mercadolibre_test.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetAutosuggestionsUseCase
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.SearchProductsScreenState
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.SearchProductsViewModel
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

class SearchProductsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutinesRule = CoroutineTestRule()
    private val observer = relaxedMockk<Observer<SearchProductsScreenState>>()
    private val getAutosuggestionsUseCase = relaxedMockk<GetAutosuggestionsUseCase>()
    private lateinit var searchProductsViewModel: SearchProductsViewModel
    private val query = "iphone"
    private val autosuggestionsResponse = Autosuggestion(
        query = query,
        suggestions = listOf(
            "Apple iPhone 11 (128 Gb) - Morado",
            "Apple iPhone 12 Pro Max (256 Gb) - Grafito",
            "Apple iPhone 12 (128 Gb) - (product)(red)"
        )
    )
    private val mockResponse = flowOf(Either.right(autosuggestionsResponse))

    @Test
    fun `given the ViewModel when it is created then should get a nothing state`() {
        val slots = mutableListOf<SearchProductsScreenState>()
        searchProductsViewModel = SearchProductsViewModel(getAutosuggestionsUseCase)
        searchProductsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }
        Assert.assertTrue(slots.first() is SearchProductsScreenState.Nothing)

        coVerify(exactly = 0) {
            getAutosuggestionsUseCase.invoke(query)
        }
    }

    @Test
    fun `given query as parameter when search method is called then should get a success state with an Autosuggestion object`() {
        val slots = mutableListOf<SearchProductsScreenState>()
        coEvery { getAutosuggestionsUseCase.invoke(query) } answers { mockResponse }
        searchProductsViewModel = SearchProductsViewModel(getAutosuggestionsUseCase)
        searchProductsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }
        Assert.assertTrue(slots.first() is SearchProductsScreenState.Nothing)

        runBlocking {
            searchProductsViewModel.search(query)
            delay(500)
        }
        coVerify { observer.onChanged(capture(slots)) }

        with((slots.last() as SearchProductsScreenState.Success).autosuggestion) {
            this shouldBeEqualTo autosuggestionsResponse
        }

        coVerify(exactly = 1) {
            getAutosuggestionsUseCase.invoke(query)
        }
    }

    @Test
    fun `given query as parameter when search method is called then should get a failure state with an AutosuggestionsException object`() {
        val slots = mutableListOf<SearchProductsScreenState>()
        coEvery { getAutosuggestionsUseCase.invoke(query) } answers { flowOf(Either.left(AutosuggestionsException.SuggestionsNotAvailable())) }
        searchProductsViewModel = SearchProductsViewModel(getAutosuggestionsUseCase)
        searchProductsViewModel.state.asLiveData().observeForever(observer)
        coVerify { observer.onChanged(capture(slots)) }
        Assert.assertTrue(slots.first() is SearchProductsScreenState.Nothing)

        runBlocking {
            searchProductsViewModel.search(query)
            delay(500)
        }
        coVerify { observer.onChanged(capture(slots)) }

        with((slots.last() as SearchProductsScreenState.Failure)) {
            this.error shouldBeInstanceOf AutosuggestionsException.SuggestionsNotAvailable::class
        }

        coVerify(exactly = 1) {
            getAutosuggestionsUseCase.invoke(query)
        }
    }
}
