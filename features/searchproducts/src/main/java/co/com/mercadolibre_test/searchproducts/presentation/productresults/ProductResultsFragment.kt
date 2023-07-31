package co.com.mercadolibre_test.searchproducts.presentation.productresults

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.mercadolibre_test.core.base.BaseFragment
import co.com.mercadolibre_test.core.utils.viewBinding
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.FragmentProductResultsBinding
import co.com.mercadolibre_test.searchproducts.domain.exceptions.ProductResultsException
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.presentation.productresults.adapter.OnProductClickListener
import co.com.mercadolibre_test.searchproducts.presentation.productresults.adapter.ProductsAdapter
import co.com.mercadolibre_test.searchproducts.utils.hide
import co.com.mercadolibre_test.searchproducts.utils.show
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductResultsFragment : BaseFragment<FragmentProductResultsBinding>(R.layout.fragment_product_results), OnProductClickListener {

    @Inject
    lateinit var viewModelAssistedFactory: ProductResultsViewModelAssistedFactory               // Se utiliza assistedInjection para proveer parametros desde la vista antes de que el viewmodel sea creado
    override val binding by viewBinding(FragmentProductResultsBinding::bind)                    // viewBinding es una extension para obtener el binding de esta pantalla y evitar el codigo boilerplate
    private val args: ProductResultsFragmentArgs by navArgs()                                   // Se trabajó con los safe args de Navigation Component para pasar parámetros entra las vistas
    private val viewModel: ProductResultsViewModel by viewModels {
        viewModelAssistedFactory.create(this, args.query)                                // Se usa la inyección asistida y se pasa como parámetro el query al ViewModel en cuestion
    }
    private val myAdapter = ProductsAdapter(this@ProductResultsFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setupViews()
        subscribeToProductsResults()
    }

    // Se configuran las vistas de la pantalla
    private fun FragmentProductResultsBinding.setupViews() {
        ivBack.setOnClickListener { onBackPressed() }
        etSearchProductsPlaceHolder.run {
            setText(args.query)
            setOnClickListener {
                findNavController().navigate(ProductResultsFragmentDirections.navigateToSearchProducts(args.query))
            }
        }
        rvProductResults.run {
            layoutManager = LinearLayoutManager(context)
            ContextCompat.getDrawable(context, R.drawable.recycler_divider)?.let { divider ->
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply { setDrawable(divider) })
            }
            adapter = myAdapter
        }
        lifecycleScope.launchWhenResumed {
            myAdapter.loadStateFlow.collectLatest {
                if (it.source.refresh is LoadState.NotLoading && it.source.prepend.endOfPaginationReached) { // Esto permite observar el estado del adaptador y saber si ya pagin ha terminado de actualizar los datos
                    if (myAdapter.itemCount == 0) {                                                          // Si los datos de pagin son vacios significa que no se encontraron resultados para mostrar
                        showEmptyWidget()
                    }
                    binding.productsShimmer.hide()
                }
                if (it.refresh is LoadState.Error) {
                    viewModel.pagingException((it.refresh as LoadState.Error).error)
                }
            }
        }
    }

    private fun subscribeToProductsResults() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.onEach(::handleProductsResponse).launchIn(this)
        }
    }

    private fun handleProductsResponse(screenState: ProductResultsScreenState) { // El estado de a vista se representa con un sealed class y acá reaccionamos a esos estados dependiendo de cuál estado se produce en el viewModel
        when (screenState) {
            ProductResultsScreenState.Loading -> {
                binding.productsShimmer.show()
                hideEmptyWidget()
            }
            is ProductResultsScreenState.Success -> {
                hideEmptyWidget()
                loadPagingData(screenState.products)
            }
            is ProductResultsScreenState.Failure -> {
                binding.productsShimmer.hide()
                hideEmptyWidget()
                handleException(screenState.error)
            }
        }
    }

    // Asignamos el PadingData el adaptador
    private fun loadPagingData(products: Flow<PagingData<Product>>) {
        lifecycleScope.launch {
            products.collect { myAdapter.submitData(it) }
        }
    }

    private fun handleException(exception: ProductResultsException) {
        when (exception) {
            is ProductResultsException.ProductsNotAvailable -> showToastMessage(exception.errorMessage)
            is ProductResultsException.UnknownError -> handleUnknownError(exception.error) // Si es un error desconocido dentro del flujo se pide a la clase padre que lo maneje
        }
    }

    private fun showEmptyWidget() {
        binding.vsProductEmpty.run {
            if (layoutResource == VIEW_STUB_NO_LAYOUT_RESULT) {
                layoutResource = R.layout.products_empty_layout
                inflate()
            }
            visibility = View.VISIBLE
        }
    }

    private fun hideEmptyWidget() {
        binding.vsProductEmpty.visibility = View.GONE
    }

    override fun onProductClick(product: Product) {
        findNavController().navigate(ProductResultsFragmentDirections.navigateToProductDetails(product))
    }
}

private const val VIEW_STUB_NO_LAYOUT_RESULT = 0
