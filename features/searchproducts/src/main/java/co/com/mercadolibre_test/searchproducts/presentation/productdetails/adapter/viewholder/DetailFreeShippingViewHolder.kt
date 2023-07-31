package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.mercadolibre_test.searchproducts.databinding.ProductDetailFreeShippingItemBinding
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView

class DetailFreeShippingViewHolder(parent: ViewGroup) : BaseProductDetailsViewHolder<DetailItemView>(
    ProductDetailFreeShippingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    override fun bind(model: DetailItemView) {}
}
