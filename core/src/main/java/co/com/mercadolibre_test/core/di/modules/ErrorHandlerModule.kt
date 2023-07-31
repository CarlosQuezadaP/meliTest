package co.com.mercadolibre_test.core.di.modules

import co.com.mercadolibre_test.core.data.ErrorHandlerImpl
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import dagger.Binds
import dagger.Module

@Module
abstract class ErrorHandlerModule {

    @Binds
    abstract fun provideErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler
}
