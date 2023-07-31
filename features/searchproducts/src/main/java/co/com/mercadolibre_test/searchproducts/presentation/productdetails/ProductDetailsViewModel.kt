package co.com.mercadolibre_test.searchproducts.presentation.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.com.mercadolibre_test.core.base.BaseViewModel
import co.com.mercadolibre_test.searchproducts.domain.models.productresults.Product
import co.com.mercadolibre_test.searchproducts.domain.usecases.GetProductDescriptionUseCase
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.*
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel @AssistedInject constructor(
    private val product: Product,
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase
) : BaseViewModel<ProductDetailsScreenState>(ProductDetailsScreenState.Loading) {

    private val _details = MutableLiveData<List<DetailItemView>>()
    val details: LiveData<List<DetailItemView>> get() = _details

    init {
        processProduct()                // Cuando abrimos la vista inmediatamente procesamos el Product que llega por parámetro para mostrar los detalles que tenemos disponibles
        getProductDescription()         // Después de procesar los detalles que tenemos disponibles, vamos a consultar la descripción
    }

    private fun processProduct() {      // Producimos la lista de detalles para mostrarla en el adaptador. Este orden está verificado con pruebas para que no sea modificado
        val detailsItemView = ArrayList<DetailItemView>()
        with(product) {
            detailsItemView.add(DetailTextItem(name))
            detailsItemView.add(DetailImageItem(image))
            detailsItemView.add(DetailPriceItem(price, originalPrice, discount, isWithoutInterest, installments?.toString()))
            if (isFreeShipping) detailsItemView.add(DetailFreeShippingItem())
        }
        _details.value = detailsItemView
    }

    private fun getProductDescription() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getProductDescriptionUseCase(product.id)
            }.collect {
                it.fold({ error ->
                    mutableState.value = ProductDetailsScreenState.Failure(error)
                }, { productDescription ->
                    mutableState.value = ProductDetailsScreenState.Success((_details.value as ArrayList<DetailItemView>).apply {
                        add(DetailTextItem(productDescription))
                    })
                })
            }
        }
    }
}
