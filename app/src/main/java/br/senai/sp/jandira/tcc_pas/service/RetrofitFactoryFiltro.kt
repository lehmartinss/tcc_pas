package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryFiltro {

        private val BASE_URL = "http://10.107.144.25:8080/v1/pas/unidades"

        private  val retrofitFactory =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        fun getFiltroService(): PasService {
            return retrofitFactory.create(PasService::class.java)
        }

}