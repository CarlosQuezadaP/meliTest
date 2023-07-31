package co.com.mercadolibre_test.core.di.modules

import co.com.mercadolibre_test.core.domain.error.ErrorRouter
import co.com.mercadolibre_test.core.domain.error.routers.NoInternetConnectionErrorRouter
import co.com.mercadolibre_test.core.domain.error.routers.UnknownErrorRouter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet

@Module
object ErrorRouterModule {

    @IntoSet
    @Reusable
    @Provides
    fun provideNoInternetConnectionErrorRouter(noInternetConnectionErrorRouter: NoInternetConnectionErrorRouter): ErrorRouter = noInternetConnectionErrorRouter

    @IntoSet
    @Reusable
    @Provides
    fun provideUnknownErrorRouter(unknownErrorRouter: UnknownErrorRouter): ErrorRouter = unknownErrorRouter
}
