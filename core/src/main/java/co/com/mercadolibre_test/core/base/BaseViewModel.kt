package co.com.mercadolibre_test.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<ViewState>(initialState: ViewState) : ViewModel() {

    /**
     * Se utiliza un StateFlow ya que, por recomendación de la doc oficial, es una excelente opción para clases que necesitan mantener un estado observable que muta.
     *
     * Para cambiar el valor se puede hacer a través de la propiedad .value
     */
    protected val mutableState = MutableStateFlow(initialState)
    val state: Flow<ViewState> get() = mutableState
}
