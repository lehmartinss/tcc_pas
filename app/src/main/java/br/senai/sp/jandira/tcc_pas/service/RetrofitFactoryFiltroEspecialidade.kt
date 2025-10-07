package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryFiltroEspecialidade {

        private val BASE_URL = "https://api-tcc-node-js-1.onrender.com/"

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