package co.com.mercadolibre_test.searchproducts.presentation.productresults.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product

class ProductsAdapter(
    private val onProductClickListener: OnProductClickListener
) : PagingDataAdapter<Product, ProductViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(parent, onProductClickListener)

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    /**
     * En las nuevas actualizaciones se recomienda usar DiffUtil y evitar llamados a notifyDataSetChanged()
     */
    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }
}
