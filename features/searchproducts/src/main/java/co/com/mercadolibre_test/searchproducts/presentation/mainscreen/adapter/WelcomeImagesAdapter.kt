package co.com.mercadolibre_test.searchproducts.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.com.mercadolibre_test.searchproducts.databinding.PagerImageContainerItemBinding
import co.com.mercadolibre_test.searchproducts.presentation.models.SliderImage

/**
 * Este adaptador se usa sólo para llenar el viewpager de la pantalla principal y dar un poco de estilo visual a la pantalla.
 */
class WelcomeImagesAdapter(private val images: List<SliderImage>) : RecyclerView.Adapter<WelcomeImagesAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        PagerImageContainerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {

        private val binding = PagerImageContainerItemBinding.bind(itemView)

        fun bind(image: SliderImage) {
            binding.ivSliderItem.run {
                setImageDrawable(ContextCompat.getDrawable(context, image.imageRes))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}
