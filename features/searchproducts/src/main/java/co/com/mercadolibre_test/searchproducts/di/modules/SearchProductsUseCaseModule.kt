package co.com.mercadolibre_test.searchproducts.di.modules

import co.com.mercadolibre_test.searchproducts.domain.repositories.AutosuggestionsRepository
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductDetailsRepository
import co.com.mercadolibre_test.searchproducts.domain.repositories.ProductsRepository
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetAutosuggestionsUseCase
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductDescriptionUseCase
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable

/***
 * Este módulo se usa para proveer únicamente los casos de uso
 */
@Module
internal object SearchProductsUseCaseModule {

    @Provides
    @Reusable
    fun provideGetAutosuggestionsUseCase(repository: AutosuggestionsRepository): GetAutosuggestionsUseCase {
        return GetAutosuggestionsUseCase(repository)
    }

    @Provides
    @Reusable
    fun provideGetProductsUseCase(repository: ProductsRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Reusable
    fun provideGetProductDescriptionUseCase(repository: ProductDetailsRepository): GetProductDescriptionUseCase {
        return GetProductDescriptionUseCase(repository)
    }
}
