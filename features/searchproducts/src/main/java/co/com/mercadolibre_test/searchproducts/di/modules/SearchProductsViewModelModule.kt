package co.com.mercadolibre_test.searchproducts.di.modules

import androidx.lifecycle.ViewModel
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.SearchProductsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/***
 * Este módulo se usa para proveer únicamente el ViewModel de SearchProductsViewModel. Los demás no necesitan estar acá porque se proveen con la inyeccion assistida
 */
@Module
abstract class SearchProductsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchProductsViewModel::class)
    abstract fun bindSearchProductsViewModel(searchProductsViewModel: SearchProductsViewModel): ViewModel
}


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
