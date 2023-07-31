package co.com.mercadolibre_test.searchproducts.mappers

import co.com.mercadolibre_test.searchproducts.data.models.ApiAutosuggestion
import co.com.mercadolibre_test.searchproducts.data.models.ApiAutosuggestionResults
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class AutosuggestionResultsMapperTest {

    private val apiAutosuggestionResults = ApiAutosuggestionResults(
        query = "iphone",
        suggestions = listOf(
            ApiAutosuggestion("iphone xr"),
            ApiAutosuggestion("iphone 6s"),
            ApiAutosuggestion("iphone 7plus")
        )
    )

    @Test
    fun `given ApiAutosuggestionResults when toAutosuggestion method is called then should transform to Autosuggestion object`() {
        val transformedObj = apiAutosuggestionResults.toAutosuggestion()

        with(transformedObj) {
            query shouldBeEqualTo "iphone"
            suggestions.size shouldBeEqualTo 3
            with(suggestions) {
                this[0] shouldBeEqualTo "iphone xr"
                this[1] shouldBeEqualTo "iphone 6s"
                this[2] shouldBeEqualTo "iphone 7plus"
            }
        }
    }
}
