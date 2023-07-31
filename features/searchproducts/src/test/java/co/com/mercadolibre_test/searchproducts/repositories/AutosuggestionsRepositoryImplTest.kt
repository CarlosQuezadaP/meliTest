package co.com.mercadolibre_test.searchproducts.repositories

import arrow.core.Either
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.data.models.ApiAutosuggestion
import co.com.mercadolibre_test.searchproducts.data.models.ApiAutosuggestionResults
import co.com.mercadolibre_test.searchproducts.data.models.autoSuggestionResponse.AutoSuggestionResponse
import co.com.mercadolibre_test.searchproducts.data.models.autoSuggestionResponse.Result
import co.com.mercadolibre_test.searchproducts.data.repositories.AutosuggestionsRepositoryImpl
import co.com.mercadolibre_test.searchproducts.data.services.AutosuggestionsService
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.testutils.relaxedMockk
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Test

class AutosuggestionsRepositoryImplTest {

    private val autosuggestionsService = relaxedMockk<AutosuggestionsService>()
    private val errorHandler = relaxedMockk<ErrorHandler>()
    private val mockQuery = "iphone"
    private val autosuggestionResults = ApiAutosuggestionResults(
        query = mockQuery,
        suggestions = listOf(
            ApiAutosuggestion("iphone xr"),
            ApiAutosuggestion("iphone 6s"),
            ApiAutosuggestion("iphone 7plus")
        )
    )

    private val autosuggestionResult = AutoSuggestionResponse(
        query = mockQuery,
        results = listOf(
            Result("iphone xr"),
            Result("iphone 6s"),
            Result("iphone 7plus")
        )
    )

    @Test
    fun `given a query as param when getAutosuggestions method is called then should get an Autosuggestion object with three autosuggestions`() {
        coEvery { autosuggestionsService.getAutosuggestions(mockQuery) }.answers { autosuggestionResult }
        val autosuggestionsRepository = AutosuggestionsRepositoryImpl(autosuggestionsService, errorHandler)
        val expectedAutosuggestion = autosuggestionResults.toAutosuggestion()

        runBlocking {
            autosuggestionsRepository.getAutosuggestions(mockQuery).collect { result ->
                result shouldBeEqualTo Either.right(expectedAutosuggestion)
                result.map {
                    it.query shouldBeEqualTo "iphone"
                    it.suggestions.size shouldBeEqualTo 3
                }
            }
        }

        coVerify(exactly = 1) {
            autosuggestionsService.getAutosuggestions(mockQuery)
        }
    }

    @Test
    fun `given a query as param when getAutosuggestions method is called then should get an Exception type AutosuggestionsException-SuggestionsNotAvailable`() {
        coEvery { autosuggestionsService.getAutosuggestions(mockQuery) } throws NetworkException.ServerError("Server error")
        val autosuggestionsRepository = AutosuggestionsRepositoryImpl(autosuggestionsService, errorHandler)

        runBlocking {
            autosuggestionsRepository.getAutosuggestions(mockQuery).collect { result ->
                Assert.assertTrue(result.isLeft())
                result.mapLeft {
                    it shouldBeInstanceOf AutosuggestionsException.SuggestionsNotAvailable::class
                    (it as AutosuggestionsException.SuggestionsNotAvailable).errorMessage shouldBeEqualTo R.string.autosuggestions_not_available
                }
            }
        }

        coVerify(exactly = 1) {
            autosuggestionsService.getAutosuggestions(mockQuery)
        }
    }

    @Test
    fun `given a query as param when getAutosuggestions method is called then should get an Exception type AutosuggestionsException-UnknownError`() {
        coEvery { autosuggestionsService.getAutosuggestions(mockQuery) } throws Exception("Server error")
        val autosuggestionsRepository = AutosuggestionsRepositoryImpl(autosuggestionsService, errorHandler)

        runBlocking {
            autosuggestionsRepository.getAutosuggestions(mockQuery).collect { result ->
                Assert.assertTrue(result.isLeft())
                result.mapLeft {
                    it shouldBeInstanceOf AutosuggestionsException.UnknownError::class
                }
            }
        }

        coVerify(exactly = 1) {
            autosuggestionsService.getAutosuggestions(mockQuery)
        }
    }
}
