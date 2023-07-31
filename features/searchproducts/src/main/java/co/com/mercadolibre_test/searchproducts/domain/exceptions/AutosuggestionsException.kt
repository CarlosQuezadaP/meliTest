package co.com.mercadolibre_test.searchproducts.domain.exceptions

import androidx.annotation.StringRes
import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.searchproducts.R


sealed class AutosuggestionsException : ErrorEntity() {

    class SuggestionsNotAvailable(@StringRes val errorMessage: Int = R.string.autosuggestions_not_available) : AutosuggestionsException()

    class UnknownError(val error: ErrorEntity) : AutosuggestionsException()
}
