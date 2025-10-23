package br.senai.sp.jandira.tcc_pas.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactoryFiltroUnidade {

        private val BASE_URL = "https://api-tcc-node-js-1.onrender.com/"

    // utilizamos isto para configuração do cliente HTTP com tempo limite maior
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private  val retrofitFactory =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        fun getUnidadeService(): PasService {
            return retrofitFactory.create(PasService::class.java)
        }

}