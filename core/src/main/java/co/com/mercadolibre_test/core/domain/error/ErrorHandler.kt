package co.com.mercadolibre_test.core.domain.error

/**
 * La implementación de esta clase permite obtener un error de negocio con base en un error desconocido que se pueda producir en la aplicación
 */
interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}
