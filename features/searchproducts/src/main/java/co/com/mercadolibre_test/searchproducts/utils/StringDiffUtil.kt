package co.com.mercadolibre_test.searchproducts.utils

import androidx.recyclerview.widget.DiffUtil

class StringDiffUtil(private val oldSuggestions: List<String>, private val newSuggestions: List<String>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldSuggestions.size

    override fun getNewListSize() = newSuggestions.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldSuggestions[oldItemPosition] == newSuggestions[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldSuggestions[oldItemPosition] == newSuggestions[newItemPosition]
}
