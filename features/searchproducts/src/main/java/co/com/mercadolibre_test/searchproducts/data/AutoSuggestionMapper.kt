package co.com.mercadolibre_test.searchproducts.data

import co.com.mercadolibre_test.searchproducts.data.models.autoSuggestionResponse.AutoSuggestionResponse
import co.com.mercadolibre_test.searchproducts.domain.models.Autosuggestion

object AutoSuggestionMapper {
    fun AutoSuggestionResponse.convertSuggestionContentToAutoSuggestion():Autosuggestion{
        return Autosuggestion(this.query,this.results.map { it.title })
    }
}
