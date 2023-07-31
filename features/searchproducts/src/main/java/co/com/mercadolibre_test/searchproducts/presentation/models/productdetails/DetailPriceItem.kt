package co.com.mercadolibre_test.searchproducts.presentation.models.productdetails

class DetailPriceItem(
    val price: Double,
    val originalPrice: Double?,
    val discount: Double?,
    val isWithoutInterest: Boolean,
    val installments: String?
) : DetailItemView(DetailItemType.ITEM_PRICE)
