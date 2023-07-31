package co.com.mercadolibre_test.searchproducts.presentation.productresults.adapter

import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product

interface OnProductClickListener {

    /**
     * Método llamado cuando se hace click en un producto de la lista
     */
    fun onProductClick(product: Product)
}
