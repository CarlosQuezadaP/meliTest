package co.com.mercadolibre_test.searchproducts.repositories

import arrow.core.Either
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.data.models.productdetails.ApiProductDetails
import co.com.mercadolibre_test.searchproducts.data.repositories.ProductDetailsRepositoryImpl
import co.com.mercadolibre_test.searchproducts.data.services.ProductDetailsService
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.testutils.relaxedMockk
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Test

class ProductDetailsRepositoryImplTest {

    private val productDetailsService = relaxedMockk<ProductDetailsService>()
    private val errorHandler = relaxedMockk<ErrorHandler>()
    private val mockProductId = "MAS721NTC17"
    private val productDetails = ApiProductDetails(
        description = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A ab, accusantium ad, consequuntur cum dolorum exercitationem facere illum in ipsa iusto maiores nemo nulla provident qui quisquam saepe similique vel!"
    )

    @Test
    fun `given a productId as param when getProductDescription method is called then should get an String as description`() {
        coEvery { productDetailsService.getProductDescription(mockProductId) }.answers { productDetails }
        val productDetailsRepository = ProductDetailsRepositoryImpl(productDetailsService, errorHandler)
        val expectedDescription =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A ab, accusantium ad, consequuntur cum dolorum exercitationem facere illum in ipsa iusto maiores nemo nulla provident qui quisquam saepe similique vel!"

        runBlocking {
            productDetailsRepository.getProductDescription(mockProductId).collect { result ->
                result shouldBeEqualTo Either.right(expectedDescription)
            }
        }

        coVerify(exactly = 1) {
            productDetailsService.getProductDescription(mockProductId)
        }
    }

    @Test
    fun `given a productId as param when getProductDescription method is called then should get an Exception type ProductDetailsException-DescriptionNotAvailable`() {
        coEvery { productDetailsService.getProductDescription(mockProductId) } throws NetworkException.ServerError("Server error")
        val productDetailsRepository = ProductDetailsRepositoryImpl(productDetailsService, errorHandler)

        runBlocking {
            productDetailsRepository.getProductDescription(mockProductId).collect { result ->
                Assert.assertTrue(result.isLeft())
                result.mapLeft {
                    it shouldBeInstanceOf ProductDetailsException.DescriptionNotAvailable::class
                    (it as ProductDetailsException.DescriptionNotAvailable).errorMessage shouldBeEqualTo R.string.product_description_empty
                }
            }
        }

        coVerify(exactly = 1) {
            productDetailsService.getProductDescription(mockProductId)
        }
    }

    @Test
    fun `given a productId as param when getProductDescription method is called then should get an Exception type ProductDetailsException-UnknownError`() {
        coEvery { productDetailsService.getProductDescription(mockProductId) } throws Exception("Server error")
        val productDetailsRepository = ProductDetailsRepositoryImpl(productDetailsService, errorHandler)

        runBlocking {
            productDetailsRepository.getProductDescription(mockProductId).collect { result ->
                Assert.assertTrue(result.isLeft())
                result.mapLeft {
                    it shouldBeInstanceOf ProductDetailsException.UnknownError::class
                }
            }
        }

        coVerify(exactly = 1) {
            productDetailsService.getProductDescription(mockProductId)
        }
    }
}
