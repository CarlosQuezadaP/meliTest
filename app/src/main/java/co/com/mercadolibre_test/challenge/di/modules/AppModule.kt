package co.com.mercadolibre_test.challenge.di.modules

import co.com.mercadolibre_test.challenge.BuildConfig
import co.com.mercadolibre_test.core.domain.EnvironmentConfig
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    /**
     * EnvironmentConfig es un objeto de core pero debe de ser proveido desde app porque el BuildConfig que contiene la baseurl es el de app
     */
    @Provides
    fun provideEnvironmentConfig() = EnvironmentConfig(BuildConfig.BASE_URL)
}
