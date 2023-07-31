package co.com.mercadolibre_test.challenge.di

import co.com.mercadolibre_test.challenge.di.modules.AppModule
import co.com.mercadolibre_test.challenge.di.scopes.AppScope
import co.com.mercadolibre_test.core.di.CoreComponent
import co.com.mercadolibre_test.core.di.modules.ErrorRouterModule
import co.com.mercadolibre_test.searchproducts.di.SearchProductsFragmentBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule


@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        SearchProductsFragmentBuilder::class,
        ErrorRouterModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance daggerApplication: DaggerApplication, coreComponent: CoreComponent): AppComponent
    }
}
