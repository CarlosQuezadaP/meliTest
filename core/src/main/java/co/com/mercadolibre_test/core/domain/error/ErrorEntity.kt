package co.com.mercadolibre_test.core.domain.error

/**
 * Se definen los errores a nivel de reglas de negociio que vamos a manejar en la aplicación
 */
abstract class ErrorEntity {

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object NoInternetConnection : ErrorEntity()

    object Unknown : ErrorEntity()
}
