package co.com.mercadolibre_test.searchproducts.domain.repositories

import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion
import kotlinx.coroutines.flow.Flow

interface AutosuggestionsRepository {

    fun getAutosuggestions(query: String): Flow<Either<AutosuggestionsException, Autosuggestion>>
}
