package co.com.mercadolibre_test.core.data

import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * En esta clase controlamos los errores que suceden en la aplicación y establecemos los errores a nivel de reglas de negocio que se van a devolver para esos casos
 */
class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        throwable.printStackTrace()             // Se imprime el error en el log para visualizarlo y hacer un mejor seguimiento cuando ocurre un problema
        return when (throwable) {
            is IOException -> ErrorEntity.NoInternetConnection
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }
}
