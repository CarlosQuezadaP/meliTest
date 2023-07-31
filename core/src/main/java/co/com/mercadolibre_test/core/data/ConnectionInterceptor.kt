package co.com.mercadolibre_test.core.data

import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.NetworkHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.HttpURLConnection

/**
 * Esta clase es agregada a OkHttpClient y permite interceptar los llamados a la red para obtener los errores, verificar el estado de red e imprimir en consola los request al servidor
 * para generar un mejor control de log y saber qué está pasando
 */
class ConnectionInterceptor(
    private val networkHelper: NetworkHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return if (networkHelper.isInternetConnectionAvailable()) {
            println(originalRequest.url())                              // Se imprime el request en el log

            val response = chain.proceed(originalRequest)

            println("${response.code()} <- ${originalRequest.url()}")       // Se imprime el codigo de respuesta y el request en el log
            println(response.peekBody(800).string().toString())   // Se imprime la respuesta en el log

            when (response.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> throw NetworkException.NotFound("Not found error")
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw NetworkException.ServerError("Internal error")
                else -> response
            }
        } else throw ConnectException("No internet connection")             // Si el helper indica que no hay conexión a internet se devuelve un error de tipo ConnectException
    }
}
