package co.com.mercadolibre_test.searchproducts.presentation.searchproducts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.mercadolibre_test.searchproducts.databinding.ProductSearchResultItemBinding

class SuggestionViewHolder(
    parent: ViewGroup,
    private val onSuggestionListener: OnSuggestionListener
) : RecyclerView.ViewHolder(ProductSearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {

    private val binding = ProductSearchResultItemBinding.bind(itemView)

    /**
     * Se muestra un simple textview con la sugerencia de búsqueda y se asigna un escuchador de evento click
     */
    fun bind(suggestion: String) {
        binding.tvSuggestion.text = suggestion
        binding.root.setOnClickListener { onSuggestionListener.onSuggestionClick(suggestion) }
    }
}
