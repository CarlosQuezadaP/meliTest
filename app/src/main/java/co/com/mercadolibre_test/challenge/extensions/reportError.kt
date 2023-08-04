package co.com.mercadolibre_test.challenge.extensions

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

fun Exception.reportError(){
    FirebaseCrashlytics.getInstance().recordException(this)
    Timber.e(this)
}