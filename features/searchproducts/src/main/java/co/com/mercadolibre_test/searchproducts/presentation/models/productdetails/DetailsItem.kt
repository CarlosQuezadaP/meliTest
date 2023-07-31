package co.com.mercadolibre_test.searchproducts.presentation.models.productdetails

import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder.ViewHolderFactory

interface DetailsItem {

    fun type(viewHolderFactory: ViewHolderFactory): Int
}
