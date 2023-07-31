package co.com.mercadolibre_test.searchproducts.di

import co.com.mercadolibre_test.searchproducts.di.modules.SearchProductsNetworkModule
import co.com.mercadolibre_test.searchproducts.di.modules.SearchProductsRepositoryModule
import co.com.mercadolibre_test.searchproducts.di.modules.SearchProductsUseCaseModule
import co.com.mercadolibre_test.searchproducts.di.modules.SearchProductsViewModelModule
import co.com.mercadolibre_test.searchproducts.presentation.mainscreen.MainFragment
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.ProductDetailsFragment
import co.com.mercadolibre_test.searchproducts.presentation.productresults.ProductResultsFragment
import co.com.mercadolibre_test.searchproducts.presentation.searchproducts.SearchProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Estos fragmentos deben aparecer acá ya que todos heredan de BaseFragment y este, a su vez, hereda de DaggerFragment. Así, dagger sabe que puede inyectar en estos fragmentos
 * lo que el desarrollador solicite y sea proveido por los módulos
 */
@Module(
    includes = [
        SearchProductsViewModelModule::class,
        SearchProductsNetworkModule::class,
        SearchProductsRepositoryModule::class,
        SearchProductsUseCaseModule::class
    ]
)
abstract class SearchProductsFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun bindProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun bindProductResultsFragment(): ProductResultsFragment

    @ContributesAndroidInjector
    abstract fun bindSearchProductsFragment(): SearchProductsFragment
}
