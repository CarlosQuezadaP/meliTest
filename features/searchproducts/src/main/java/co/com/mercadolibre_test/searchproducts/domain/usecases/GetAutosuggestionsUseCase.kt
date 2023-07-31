package co.com.mercadolibre_test.searchproducts.domain.usecases

import arrow.core.Either
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion
import co.com.mercadolibre_test.searchproducts.domain.repositories.AutosuggestionsRepository
import kotlinx.coroutines.flow.Flow


class GetAutosuggestionsUseCase(
    private val repository: AutosuggestionsRepository
) {

    operator fun invoke(query: String): Flow<Either<AutosuggestionsException, Autosuggestion>> {
        return repository.getAutosuggestions(query)
    }
}
