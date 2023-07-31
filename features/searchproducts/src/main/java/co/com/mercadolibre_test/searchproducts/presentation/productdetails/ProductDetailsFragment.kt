package co.com.mercadolibre_test.searchproducts.presentation.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.mercadolibre_test.core.base.BaseFragment
import co.com.mercadolibre_test.core.utils.viewBinding
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.FragmentProductDetailsBinding
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductDetailsException
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.ProductDetailsAdapter
import co.com.mercadolibre_test.searchproducts.utils.hide
import co.com.mercadolibre_test.searchproducts.utils.show
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>(R.layout.fragment_product_details) {

    @Inject
    lateinit var viewModelAssistedFactory: ProductDetailsViewModelAssistedFactory               // Se utiliza assistedInjection para proveer parametros desde la vista antes de que el viewmodel sea creado
    override val binding by viewBinding(FragmentProductDetailsBinding::bind)                    // viewBinding es una extension para obtener el binding de esta pantalla y evitar el codigo boilerplate
    private val args: ProductDetailsFragmentArgs by navArgs()                                   // Se trabajó con los safe args de Navigation Component para pasar parámetros entra las vistas
    private val viewModel: ProductDetailsViewModel by viewModels {
        viewModelAssistedFactory.create(this, args.product)                              // Se usa la inyección asistida y se pasa como parámetro el Product al ViewModel en cuestion
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setupViews()
        subscribeToDetails()
        subscribeToDescription()
    }

    // Se configuran las vistas de la pantalla
    private fun FragmentProductDetailsBinding.setupViews() {
        ivBack.setOnClickListener { onBackPressed() }
        rvProductDetails.layoutManager = LinearLayoutManager(context)
    }

    //
    private fun subscribeToDetails() {
        lifecycleScope.launchWhenCreated {                          // Iniciamos a observar los detalles del viewModel cuando el estado del lifecycleScope está al menos en Lifecycle.State.CREATED
            viewModel.details.observe(viewLifecycleOwner, ::handleDetails)
        }
    }

    private fun subscribeToDescription() {                          // Iniciamos a observar el estado de la vista del viewModel cuando el estado del lifecycleScope está al menos en Lifecycle.State.CREATED
        lifecycleScope.launchWhenCreated {
            viewModel.state.onEach(::handleScreenState).launchIn(this)
        }
    }

    private fun handleDetails(details: List<DetailItemView>) {      // Se cargan los datos en el adaptador
        binding.rvProductDetails.adapter = ProductDetailsAdapter(details)
    }

    private fun handleScreenState(screenState: ProductDetailsScreenState) {     // El estado de a vista se representa con un sealed class y acá reaccionamos a esos estados dependiendo de cuál estado se produce en el viewModel
        when (screenState) {
            ProductDetailsScreenState.Loading -> binding.detailsShimmer.show()
            is ProductDetailsScreenState.Success -> {
                binding.detailsShimmer.hide()
                handleDetails(screenState.details)
            }
            is ProductDetailsScreenState.Failure -> {
                binding.detailsShimmer.hide()
                handleException(screenState.error)
            }
        }
    }

    private fun handleException(exception: ProductDetailsException) {
        when (exception) {
            is ProductDetailsException.DescriptionNotAvailable -> showToastMessage(exception.errorMessage)
            is ProductDetailsException.UnknownError -> handleUnknownError(exception.error)  // Si en nuestro flujo no conocemos el error, le pedimos a la clase padre que lo maneje
        }
    }
}
