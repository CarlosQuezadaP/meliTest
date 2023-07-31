package co.com.mercadolibre_test.core.domain

/**
 * En esta clase establecemos los diferentes parámetros de configuración que tendrá nuestra aplicación.
 *
 * En este caso, sólo proveemos la baseURL de la aplicación pero se podrían agregar muchos otros campos, si fuese necesario.
 */
data class EnvironmentConfig(
    val baseURL: String
)
