package co.com.mercadolibre_test.searchproducts.presentation.searchproducts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.mercadolibre_test.core.base.BaseFragment
import co.com.mercadolibre_test.core.base.ViewModelFactory
import co.com.mercadolibre_test.core.utils.viewBinding
import co.com.mercadolibre_test.searchproducts.R
import co.com.mercadolibre_test.searchproducts.databinding.FragmentSearchProductsBinding
import co.com.mercadolibre_test.searchproducts.domain.exceptions.AutosuggestionsException
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.adapter.OnSuggestionListener
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.adapter.SuggestionsAdapter
import co.com.mercadolibre_test.searchproducts.utils.SearchTextWatcher
import co.com.mercadolibre_test.searchproducts.utils.addSearchActionListener
import co.com.mercadolibre_test.searchproducts.utils.showKeyBoard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchProductsFragment : BaseFragment<FragmentSearchProductsBinding>(R.layout.fragment_search_products), OnSuggestionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory             // Se provee una implementación de ViewModelProvider.Factory para pasarla a la extensión viewModels y así poder inyectar dependencias en el constructor del viewModel
    override val binding by viewBinding(FragmentSearchProductsBinding::bind) // viewBinding es una extension para obtener el binding de esta pantalla y evitar el codigo boilerplate
    private val args: SearchProductsFragmentArgs by navArgs()                // Se trabajó con los safe args de Navigation Component para pasar parámetros entra las vistas
    private val viewModel: SearchProductsViewModel by viewModels { viewModelFactory }
    private val suggestionsAdapter = SuggestionsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setupViews()
        subscribeToAutosuggestions()
    }

    // Se configuran las vistas de la pantalla
    private fun FragmentSearchProductsBinding.setupViews() {
        ivBack.setOnClickListener { onBackPressed() }
        etSearchProducts.run {
            addTextChangedListener(SearchTextWatcher(viewModel::search))
            addSearchActionListener(::navigateToProductResults)
            args.oldQuery?.let { setText(it) }
            requestFocus()
            showKeyBoard(this)
        }
        rvSearchProducts.run {
            adapter = suggestionsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeToAutosuggestions() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.onEach(::handleAutosuggestions).launchIn(this)
        }
    }

    private fun handleAutosuggestions(screenState: SearchProductsScreenState) {
        when (screenState) {
            is SearchProductsScreenState.Success -> suggestionsAdapter.updateList(screenState.autosuggestion.suggestions)
            is SearchProductsScreenState.Failure -> processError(screenState.error)
            // Data la configuración del viewModel, se necesita proveer un estado inicial de la vista. Como esta vista no hace nada cuando se abre inicialmente, se utiliza este estado para indicar que la vista no hace nada en ese estado.
            SearchProductsScreenState.Nothing -> { }
        }
    }

    override fun onSuggestionClick(suggestionQuery: String) {
        navigateToProductResults(suggestionQuery)
    }

    private fun processError(error: AutosuggestionsException) {
        when (error) {
            is AutosuggestionsException.SuggestionsNotAvailable -> showToastMessage(error.errorMessage)
            is AutosuggestionsException.UnknownError -> handleUnknownError(error.error) // Si el error es desconocido dentro de la logica del flujo, se pide a la clase padre que lo maneje
        }
    }

    // Se navega a la siguiente pantalla en el grafico
    private fun navigateToProductResults(query: String) {
        if (query.isNotEmpty()) {
            findNavController().navigate(SearchProductsFragmentDirections.navigateToProductResults(query))
        }
    }
}
