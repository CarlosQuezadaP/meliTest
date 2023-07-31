package co.com.mercadolibre_test.searchproducts.presentation.mainscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import co.com.mercadolibre_test.core.base.BaseFragment
import co.com.mercadolibre_test.core.utils.viewBinding
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.FragmentMainBinding
import co.com.mercadolibre_test.searchproducts.presentation.mainscreen.adapter.WelcomeImagesAdapter
import co.com.mercadolibre_test.searchproducts.presentation.models.SliderImage

/**
 * este fragmento no contiene logica de negocio. Sólo se usa para ambientar al usuario en la aplicación.
 */
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val binding by viewBinding(FragmentMainBinding::bind)
    private val images = listOf(
        SliderImage(R.drawable.img_1),
        SliderImage(R.drawable.img_2),
        SliderImage(R.drawable.img_3),
        SliderImage(R.drawable.img_4)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etSearchProductsPlaceHolder.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.navigateToSearchProducts(null))
        }
        binding.vpWelcomeImages.run {
            adapter = WelcomeImagesAdapter(images)
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(MarginPageTransformer(10))
        }
    }
}
