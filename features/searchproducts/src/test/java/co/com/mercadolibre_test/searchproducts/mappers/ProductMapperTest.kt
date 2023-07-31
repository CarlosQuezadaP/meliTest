package co.com.mercadolibre_test.searchproducts.mappers

import co.com.mercadolibre_test.searchproducts.data.models.productresults.ApiDifferentialPricing
import co.com.mercadolibre_test.searchproducts.data.models.productresults.ApiInstallments
import co.com.mercadolibre_test.searchproducts.data.models.productresults.ApiProduct
import co.com.mercadolibre_test.searchproducts.data.models.productresults.ApiShipping
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class ProductMapperTest {

    private val apiProduct = ApiProduct(
        id = "ASJDFHNO234H8HO",
        name = "Apple iPhone 11 (128 Gb) - Morado",
        price = 58900,
        originalPrice = 67999,
        image = "https://api.mercadolibre.com/images/img1.jpg",
        installments = ApiInstallments(36, 99.98),
        shipping = ApiShipping(true),
        differentialPricing = ApiDifferentialPricing("ASUYX71263")
    )

    @Test
    fun `given ApiProduct when toProduct method is called then should transform to Product object`() {
        val transformedObj = apiProduct.toProduct()

        with(transformedObj) {
            id shouldBeEqualTo "ASJDFHNO234H8HO"
            name shouldBeEqualTo "Apple iPhone 11 (128 Gb) - Morado"
            price shouldBeEqualTo 58900
            originalPrice shouldBeEqualTo 67999
            discount shouldBeEqualTo 13
            image shouldBeEqualTo "https://api.mercadolibre.com/images/img1.jpg"
            installments!!.quantity shouldBeEqualTo 36
            installments!!.amount shouldBeEqualTo 99.98
            isFreeShipping shouldBeEqualTo true
            isWithoutInterest shouldBeEqualTo true
        }
    }

    @Test
    fun `given ApiProduct with originalPrice=null when toProduct method is called then should transform to Product object with discount=null`() {
        val transformedObj = apiProduct.copy(originalPrice = null).toProduct()

        with(transformedObj) {
            originalPrice shouldBeEqualTo null
            discount shouldBeEqualTo null
        }
    }

    @Test
    fun `given ApiProduct with ApiShipping-freeShipping=false when toProduct method is called then should transform to Product object with isFreeShipping=false`() {
        val transformedObj = apiProduct.copy(shipping = ApiShipping(false)).toProduct()

        transformedObj.isFreeShipping shouldBeEqualTo false
    }

    @Test
    fun `given ApiProduct with differentialPricing=null when toProduct method is called then should transform to Product object with isWithoutInterest=false`() {
        val transformedObj = apiProduct.copy(differentialPricing = null).toProduct()

        transformedObj.isWithoutInterest shouldBeEqualTo false
    }
}
