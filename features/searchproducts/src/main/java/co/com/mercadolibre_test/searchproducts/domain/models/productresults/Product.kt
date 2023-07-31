package co.com.mercadolibre_test.searchproducts.domain.models.productresults

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double?,
    val discount: Double?,
    val image: String,
    val installments: Installments?,
    val isFreeShipping: Boolean,
    val isWithoutInterest: Boolean
): Parcelable
