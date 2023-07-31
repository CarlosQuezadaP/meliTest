package co.com.mercadolibre_test.searchproducts.di.modules

import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.searchproducts.data.repositories.AutosuggestionsRepositoryImpl
import co.com.mercadolibre_test.searchproducts.data.repositories.ProductDetailsRepositoryImpl
import co.com.mercadolibre_test.searchproducts.data.repositories.ProductsRepositoryImpl
import co.com.mercadolibre_test.searchproducts.data.services.AutosuggestionsService
import co.com.mercadolibre_test.searchproducts.data.services.ProductDetailsService
import co.com.mercadolibre_test.searchproducts.data.services.ProductsService
import co.com.mercadolibre_test.searchproducts.domain.repositories.AutosuggestionsRepository
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductDetailsRepository
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
/***
 * Este módulo se usa para proveer únicamente los repositorios
 */
@Module
internal object SearchProductsRepositoryModule {

    @Provides
    @Reusable
    fun provideAutosuggestionsRepository(service: AutosuggestionsService, errorHandler: ErrorHandler): AutosuggestionsRepository {
        return AutosuggestionsRepositoryImpl(service, errorHandler)
    }

    @Provides
    @Reusable
    fun provideProductsRepository(service: ProductsService): ProductsRepository {
        return ProductsRepositoryImpl(service)
    }

    @Provides
    @Reusable
    fun provideProductDetailsRepository(service: ProductDetailsService, errorHandler: ErrorHandler): ProductDetailsRepository {
        return ProductDetailsRepositoryImpl(service, errorHandler)
    }
}
