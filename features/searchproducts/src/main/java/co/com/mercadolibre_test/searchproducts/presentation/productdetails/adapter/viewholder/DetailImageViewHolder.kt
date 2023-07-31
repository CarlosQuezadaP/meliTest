package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.mercadolibre_test.searchproducts.databinding.ProductDetailImageItemBinding
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailImageItem
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView
import co.com.mercadolibre_test.searchproducts.utils.loadImageFromUrl

class DetailImageViewHolder(parent: ViewGroup) : BaseProductDetailsViewHolder<DetailItemView>(
    ProductDetailImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ProductDetailImageItemBinding.bind(itemView)

    override fun bind(model: DetailItemView) {
        with(model as DetailImageItem) {
            binding.ivProductDetailImage.loadImageFromUrl(url)
        }
    }
}
