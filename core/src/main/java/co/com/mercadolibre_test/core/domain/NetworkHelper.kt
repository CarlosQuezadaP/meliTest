package co.com.mercadolibre_test.core.domain


/**
 * Esta clase nos permite proveer un helper para consultar el estado de la red
 */
interface NetworkHelper {

    fun isInternetConnectionAvailable(): Boolean
}
