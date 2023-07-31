package co.com.mercadolibre_test.core.domain.error

import android.content.Context

/**
 * Esta clase se usa para menejar los errores desconocidos de los flujos y así tomar una desición de qué hacer o qué mostrar cuando llegan esos errores
 */
abstract class ErrorRouter {

    fun render(error: ErrorEntity, context: Context) {
        if (!isApplicable(error)) return // Se verifica si el error se puede manejar por el router

        renderInternal(error, context)   // Si se puede manejar el error, entonces se renderiza el error
    }

    abstract fun isApplicable(error: ErrorEntity): Boolean              // Verifica si el error de negocio que ha llegado se puede manejar por el router

    abstract fun renderInternal(error: ErrorEntity, context: Context)   // En caso de que el error sea manejable por el router, se renderiza el error
}
