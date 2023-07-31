package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.mercadolibre_test.searchproducts.databinding.ProductDetailNameItemBinding
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailTextItem

class DetailTitleViewHolder(parent: ViewGroup) : BaseProductDetailsViewHolder<DetailItemView>(
    ProductDetailNameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ProductDetailNameItemBinding.bind(itemView)

    override fun bind(model: DetailItemView) {
        with(model as DetailTextItem) {
            binding.tvProductDetailName.text = this.name
        }
    }
}
