package co.com.mercadolibre_test.core.di

import co.com.mercadolibre_test.core.di.modules.ContextModule
import co.com.mercadolibre_test.core.di.modules.CoreModule
import co.com.mercadolibre_test.core.di.modules.ErrorHandlerModule
import co.com.mercadolibre_test.core.di.scopes.CoreScope
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@CoreScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ContextModule::class,
        CoreModule::class,
        ErrorHandlerModule::class
    ]
)
interface CoreComponent : AndroidInjector<DaggerApplication> {

    fun provideRetrofit(): Retrofit                 // Se provee una instancia de retrofit ya que es usada por un módulo que depende de core

    fun provideErrorHandler(): ErrorHandler         // Se provee una instancia de ErrorHandler ya que es usada por un módulo que depende de core

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: DaggerApplication): CoreComponent
    }
}
