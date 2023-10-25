package com.example.clienteappapi.Di

import com.example.clienteappapi.Data.Remote.ClienteApi
import com.example.clienteappapi.Data.Repository.ClienteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

object AppModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        @Singleton
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        @Singleton
        fun provideClienteApi(moshi: Moshi): ClienteApi {
            return Retrofit.Builder()
                .baseUrl("http://clientes-api.somee.com")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(ClienteApi::class.java)
        }

        @Provides
        @Singleton
        fun provideClienteRepository(clienteApi: ClienteApi): ClienteRepository {
            return ClienteRepository(clienteApi)
        }


    }

}