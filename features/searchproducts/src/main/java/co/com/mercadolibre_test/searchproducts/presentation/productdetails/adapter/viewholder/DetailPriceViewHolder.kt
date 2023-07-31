package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.com.mercadolibre_test.core.utils.formatPrice
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.ProductDetailPriceItemBinding
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailPriceItem
import co.com.mercadolibre_test.searchproducts.utils.DISCOUNT_COMPLEMENT

class DetailPriceViewHolder(parent: ViewGroup) : BaseProductDetailsViewHolder<DetailItemView>(
    ProductDetailPriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ProductDetailPriceItemBinding.bind(itemView)

    override fun bind(model: DetailItemView) {
        with(model as DetailPriceItem) {
            originalPrice?.let {
                binding.tvProductDetailOriginalPrice.text = formatPrice(it)
                binding.tvProductDetailOriginalPrice.visibility = View.VISIBLE
            }
            binding.tvProductDetailPrice.text = formatPrice(price)
            discount?.let {
                val discountComplemented = "$it$DISCOUNT_COMPLEMENT"
                binding.tvProductDetailDiscount.text = discountComplemented
                binding.tvProductDetailDiscount.visibility = View.VISIBLE
            }
            installments?.let {
                binding.tvProductDetailInstallments.visibility = View.VISIBLE
                if (isWithoutInterest) {
                    binding.tvProductDetailInstallments.run {
                        text = String.format(context.getString(R.string.installment_label_without_interest, it))
                        setTextColor(ContextCompat.getColor(context, R.color.mercado_libre_green_color))
                    }
                } else {
                    binding.tvProductDetailInstallments.run {
                        text = String.format(context.getString(R.string.installment_label, it))
                    }
                }
            }
        }
    }
}
