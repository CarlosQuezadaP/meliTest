package co.com.mercadolibre_test.challenge

import co.com.mercadolibre_test.challenge.di.DaggerAppComponent
import co.com.mercadolibre_test.core.EnvironmentConfigProvider
import co.com.mercadolibre_test.core.di.DaggerCoreComponent
import co.com.mercadolibre_test.core.domain.EnvironmentConfig
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MercadolibreApplication : DaggerApplication(), EnvironmentConfigProvider {

    private val coreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this, coreComponent)
    }

    override fun getEnvironmentConfig() = EnvironmentConfig(BuildConfig.BASE_URL)
}
