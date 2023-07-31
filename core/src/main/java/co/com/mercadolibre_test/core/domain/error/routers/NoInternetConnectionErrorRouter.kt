package co.com.mercadolibre_test.core.domain.error.routers

import android.content.Context
import android.widget.Toast
import co.com.mercadolibre_test.core.R
import co.com.mercadolibre_test.core.domain.error.ErrorEntity
import co.com.mercadolibre_test.core.domain.error.ErrorRouter
import javax.inject.Inject

/**
 * Esta clase maneja el error de tipo NoInternetConnection
 */
class NoInternetConnectionErrorRouter @Inject constructor() : ErrorRouter() {

    override fun isApplicable(error: ErrorEntity): Boolean {
        return error is ErrorEntity.NoInternetConnection
    }

    override fun renderInternal(error: ErrorEntity, context: Context) {
        Toast.makeText(context, context.getString(R.string.no_internet_error), Toast.LENGTH_SHORT).show()
    }
}
