package co.com.mercadolibre_test.core

import co.com.mercadolibre_test.core.domain.EnvironmentConfig

/**
 * Esta clase se utiliza para proveer las diferentes configuraciones de la aplicación.
 *
 * En este caso sólo se usa para proveer la baseURL del proyecto.
 *
 * Si se tuvieran diferentes ambientes de compilación, esta clase sería la encargada de proveer esas configuraciones.
 */
interface EnvironmentConfigProvider {

    fun getEnvironmentConfig(): EnvironmentConfig
}
