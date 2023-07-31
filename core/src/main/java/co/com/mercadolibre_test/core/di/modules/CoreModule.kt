package co.com.mercadolibre_test.core.di.modules

import android.content.Context
import co.com.mercadolibre_test.core.EnvironmentConfigProvider
import co.com.mercadolibre_test.core.data.ConnectionInterceptor
import co.com.mercadolibre_test.core.data.NetworkHelperImpl
import co.com.mercadolibre_test.core.domain.EnvironmentConfig
import co.com.mercadolibre_test.core.domain.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
object CoreModule {

    @Provides
    fun provideNetworkHelper(context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    fun provideEnvironmentConfig(
        daggerApplication: DaggerApplication
    ): EnvironmentConfig {
        return (daggerApplication as EnvironmentConfigProvider).getEnvironmentConfig()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideOkHttpClient(
        networkHelper: NetworkHelper
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectionInterceptor(networkHelper))
            .build()
    }

    @Provides
    fun provideRetrofit(
        environmentConfig: EnvironmentConfig,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(environmentConfig.baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }
}
