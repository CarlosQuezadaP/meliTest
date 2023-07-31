package co.com.mercadolibre_test.searchproducts.presentation.searchproducts.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.com.mercadolibre_test.searchproducts.utils.StringDiffUtil

class SuggestionsAdapter(private val onSuggestionListener: OnSuggestionListener) : RecyclerView.Adapter<SuggestionViewHolder>() {

    private var suggestions: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SuggestionViewHolder(parent, onSuggestionListener)

    override fun getItemCount(): Int = suggestions.size

    override fun onBindViewHolder(viewHolder: SuggestionViewHolder, position: Int) {
        viewHolder.bind(suggestions[position])
    }

    /**
     * Este método siempre se llamará cuando se hace una nueva consulta en la barra de búsqueda de productos, así, siempre se muestran las nuevas sugerencias
     *
     * Se usa diffUtil por recomendacion de la plataforma ya que notifyDataSetChanged se encuentra deprecado
     */
    fun updateList(newSuggestions: List<String>) {
        val diffCallback = StringDiffUtil(this.suggestions, newSuggestions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        this.suggestions = newSuggestions
    }
}
