package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.ViewGroup
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemType
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView

class ViewHolderFactoryImpl : ViewHolderFactory {

    /**
     * Este método devuelve un DetailItemView de acuerdo al viewType
     */
    override fun create(parent: ViewGroup, viewType: Int): BaseProductDetailsViewHolder<DetailItemView> {
        return when (viewType) {
            DetailItemType.ITEM_TEXT.value -> DetailTitleViewHolder(parent)
            DetailItemType.ITEM_IMAGE.value -> DetailImageViewHolder(parent)
            DetailItemType.ITEM_PRICE.value -> DetailPriceViewHolder(parent)
            DetailItemType.ITEM_FREE_SHIPPING.value -> DetailFreeShippingViewHolder(parent)
            else -> throw Exception("No DetailViewHolder found for viewType: $viewType")
        }
    }

    /**
     * Este método es llamado desde el adaptador para obtener el viewType que se desea bindear en el momento
     */
    override fun type(detailItemView: DetailItemView) = detailItemView.type.value
}
