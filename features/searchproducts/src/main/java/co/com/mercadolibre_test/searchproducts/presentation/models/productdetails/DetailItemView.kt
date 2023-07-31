package co.com.mercadolibre_test.searchproducts.presentation.models.productdetails

import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder.ViewHolderFactory

abstract class DetailItemView(open val type: DetailItemType) : DetailsItem {

    override fun type(viewHolderFactory: ViewHolderFactory) = viewHolderFactory.type(this)
}
