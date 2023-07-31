package co.com.mercadolibre_test.core.domain

import java.io.IOException


/**
 * Con esta clase definimos los tipos de errores que vamos a manejar cuando tenemos un error de tipo IOException
 */
sealed class NetworkException(errorMessage: String?) : IOException(errorMessage) {

    class NotFound(errorMessage: String?) : NetworkException(errorMessage)

    class ServerError(errorMessage: String?) : NetworkException(errorMessage)
}
