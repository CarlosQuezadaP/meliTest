package co.com.mercadolibre_test.core.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.DaggerApplication

@Module
abstract class ContextModule {

    @Binds
    abstract fun provideApplication(daggerApplication: DaggerApplication): Application

    @Binds
    abstract fun provideContext(daggerApplication: DaggerApplication): Context
}
