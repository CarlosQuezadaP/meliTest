package co.com.mercadolibre_test.searchproducts.di.modules

import co.com.mercadolibre_test.searchproducts.data.services.AutosuggestionsService
import co.com.mercadolibre_test.searchproducts.data.services.ProductDetailsService
import co.com.mercadolibre_test.searchproducts.data.services.ProductsService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

/***
 * Este módulo se usa para proveer únicamente los servicios
 */
@Module
internal object SearchProductsNetworkModule {

    @Provides
    @Reusable
    fun provideAutosuggestionsService(retrofit: Retrofit): AutosuggestionsService {
        return retrofit.create(AutosuggestionsService::class.java)
    }

    @Provides
    @Reusable
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Reusable
    fun provideProductDetailsService(retrofit: Retrofit): ProductDetailsService {
        return retrofit.create(ProductDetailsService::class.java)
    }
}
