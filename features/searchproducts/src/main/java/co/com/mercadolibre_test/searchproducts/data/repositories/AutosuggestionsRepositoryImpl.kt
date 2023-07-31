package co.com.mercadolibre_test.searchproducts.data.repositories

import arrow.core.Either
import co.com.mercadolibre_test.core.domain.NetworkException
import co.com.mercadolibre_test.core.domain.error.ErrorHandler
import co.com.mercadolibre_test.core.utils.reportError
import co.com.mercadolibre_test.searchproducts.data.AutoSuggestionMapper.convertSuggestionContentToAutoSuggestion
import co.com.mercadolibre_test.searchproducts.data.services.AutosuggestionsService
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.domain.repositories.AutosuggestionsRepository
import kotlinx.coroutines.flow.flow

class AutosuggestionsRepositoryImpl(
    private val service: AutosuggestionsService,
    private val errorHandler: ErrorHandler
) : AutosuggestionsRepository {

    /**
     * Either es un objeto de la libería arror que permite encapsular dos tipos de respuesta en su contenido. se devuelve un left cuando ocurre algún
     * error y se devuelve right cuando el proceso es exitoso.
     */
    override fun getAutosuggestions(query: String) = flow {
        emit(
            try {
                Either.right(service.getAutosuggestions(query).convertSuggestionContentToAutoSuggestion())
            } catch (exception: Exception) {
                exception.reportError()
                Either.left(
                    if (exception is NetworkException) {
                        AutosuggestionsException.SuggestionsNotAvailable()
                    } else
                        AutosuggestionsException.UnknownError(errorHandler.getError(exception))
                )
            }
        )
    }
}
