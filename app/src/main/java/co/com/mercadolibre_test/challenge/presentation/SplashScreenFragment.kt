package co.com.mercadolibre_test.challenge.presentation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.com.mercadolibre_test.challenge.R
import co.com.mercadolibre_test.challenge.databinding.FragmentSplashScreenBinding
import co.com.mercadolibre_test.core.utils.viewBinding

/**
 * Esta clase se usa sólo con fines visuales para dar una buena primera impresión a los usuarios de nuestra app
 */
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private val binding by viewBinding(FragmentSplashScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            // Se crea una animación para el icono de mercado libre
            val iconAnimator = ObjectAnimator.ofFloat(
                ivIcon,
                ICON_PROPERTY_NAME_TO_ANIMATE,
                ICON_PROPERTY_NAME_TO_ANIMATE_END_VALUE * resources.displayMetrics.density
            ).apply {
                duration = ANIMATION_LENGTH_SHORT
                interpolator = AccelerateDecelerateInterpolator()
            }

            // Se crea una animación para el texto de bienvenida de mercado libre
            val welcomeMessageAnimator = ObjectAnimator.ofFloat(
                tvWelcomeMessage,
                WELCOME_MESSAGE_PROPERTY_NAME_TO_ANIMATE,
                WELCOME_MESSAGE_PROPERTY_NAME_TO_ANIMATE_END_VALUE
            ).apply { duration = ANIMATION_LENGTH_LONG }

            // Se crea un animator set para animar el icono y el mensaje al mismo tiempo
            AnimatorSet().apply {
                startDelay = ANIMATION_LENGTH_SHORT
                play(welcomeMessageAnimator).with(iconAnimator)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {
                    }

                    override fun onAnimationEnd(p0: Animator) {
                        // Cuando terminamos la animación navegamos en el grafo a la pantalla principal
                        navigateToProductSearchView()
                    }

                    override fun onAnimationCancel(p0: Animator) {
                    }

                    override fun onAnimationRepeat(p0: Animator) {
                    }
                })
                start()
            }
        }
    }

    private fun navigateToProductSearchView() {
        findNavController().navigate(SplashScreenFragmentDirections.navigateToSearchProducts())
    }
}

private const val ANIMATION_LENGTH_LONG = 1000L
private const val ANIMATION_LENGTH_SHORT = 500L
private const val ICON_PROPERTY_NAME_TO_ANIMATE = "translationY"
private const val ICON_PROPERTY_NAME_TO_ANIMATE_END_VALUE = -100F
private const val WELCOME_MESSAGE_PROPERTY_NAME_TO_ANIMATE = "alpha"
private const val WELCOME_MESSAGE_PROPERTY_NAME_TO_ANIMATE_END_VALUE = 1F
