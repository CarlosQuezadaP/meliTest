package co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.mercadolibre_test.searchproducts.presentation.models.productdetails.DetailItemView
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder.BaseProductDetailsViewHolder
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder.ViewHolderFactory
import co.com.mercadolibre_test.searchproducts.presentation.productdetails.adapter.viewholder.ViewHolderFactoryImpl

/**
 * Este adaptador muestra los detalles de un producto que se construyen dinámicamente y son de varios tipos. Los items que se muestran acá heredan de
 * DetailItemView. En este fragmento se pueden mostrar n tipos de vista que hereden de DetailItemView. el ViewHolderFactory se encarga de cargar el item específico
 * con base en el getItemViewType
 */
class ProductDetailsAdapter(
    private val details: List<DetailItemView>
) : RecyclerView.Adapter<BaseProductDetailsViewHolder<DetailItemView>>() {

    private val viewHolderFactory: ViewHolderFactory = ViewHolderFactoryImpl()      // Factory que obtiene el tipo de item de acuerdo a el viewType

    override fun getItemViewType(position: Int) = details[position].type(viewHolderFactory)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseProductDetailsViewHolder<DetailItemView> {
        return viewHolderFactory.create(parent, viewType)                           // El Factory obtiene el ItemView con bae en el ViewType
    }

    override fun getItemCount() = details.size

    override fun onBindViewHolder(holder: BaseProductDetailsViewHolder<DetailItemView>, position: Int) {
        holder.bind(details[position])
    }
}
