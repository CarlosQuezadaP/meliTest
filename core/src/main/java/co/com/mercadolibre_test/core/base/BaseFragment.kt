package co.com.mercadolibre_test.core.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.core.domain.error.ErrorRouter
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment<viewBinding : ViewBinding>(@LayoutRes resId: Int) : DaggerFragment(resId) {

    @Inject
    lateinit var errorRouters: MutableSet<ErrorRouter>                  // Esta variable contiene todos los routers para manejar los errores que no son controlados por los flujos
    protected abstract val binding: viewBinding                         // Se establece la variable para que todos los fragmentos tengan la misma estructura y evitar el codigo boilerplate

    protected fun showToastMessage(@StringRes message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    protected fun onBackPressed() {
        requireActivity().onBackPressed()
    }

    /**
     * Cuando se recibe un error no controlado por algun flujo específico, se busca en los routers quien lo puede manejar y se renderiza el error
     *
     * Los routers son proveidos en el mpodulo ErrorRouterModule con la anotacion @IntoSet para que se agreguen todos a MutableSet<ErrorRouter>
     */
    fun handleUnknownError(error: ErrorEntity) {
        errorRouters.filter { it.isApplicable(error) }.forEach {
            it.render(error, requireContext())
        }
    }
}
