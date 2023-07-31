package co.com.mercadolibre_test.searchproducts.utils

import android.text.Editable
import android.text.TextWatcher
import co.com.mercadolibre_test.core.utils.EMPTY
import java.util.*


/**
 * Exta extensión agrega la posibilidad de reaccionar a los cambios de texto en un EditText cuando han pasado
 * 300 milisegundos desde que el usuario dejó de escribir en el campo
 */
class SearchTextWatcher(private val onTimerEnds: (String) -> Unit) : TextWatcher {

    // Con este timer nos aseguramos de contar siempre 300 milisegundos para llamar a la función onTimerEnds
    private var timer: Timer? = null

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Si el texto cambia cancelamos el contador que haya en curso para iniciarlo nuevamente
        timer?.cancel()
    }

    /**
     * Siempre que el texto cambie se inicia un timer con una duración de 300 milisegundos.
     * Cuando este termina, se llama la función onTimerEnds siempre y cuando el texto no esté vacío
     */
    override fun afterTextChanged(editable: Editable?) {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                val query = editable.toString()
                if (query != EMPTY) onTimerEnds(query)
            }
        }, SEARCH_TEXT_WATCHER_DELAY)
    }
}

private const val SEARCH_TEXT_WATCHER_DELAY = 300L
