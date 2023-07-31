package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.ViewGroup
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView

interface ViewHolderFactory {

    fun create(parent: ViewGroup, viewType: Int): BaseProductDetailsViewHolder<DetailItemView>

    fun type(detailItemView: DetailItemView): Int
}
