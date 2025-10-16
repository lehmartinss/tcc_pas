package br.senai.sp.jandira.tcc_pas.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactoryOSM {

        private val BASE_URL = "https://nominatim.openstreetmap.org/"

        // Criando o client com User-Agent
        private val client = OkHttpClient.Builder()
            .addInterceptor { chain -> // chain: fila de interceptores
                val request = chain.request().newBuilder() // chain.request (pega a requisicao original q ia ser enviada) e .newBuilder permite adicionar coisas dentro
                    .header(
                        "User-Agent",
                        "PAS-TCC/1.0 (nicolasalmeidasantos@outlook.com.br)"
                    )
                    .build() // termina
                chain.proceed(request) // continua com a requisição
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        private  val retrofitFactory =
            Retrofit
                .Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        fun getOSMService(): PasService {
            return retrofitFactory.create(PasService::class.java)
        }

}