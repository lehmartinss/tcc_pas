package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryOSM {

        private val BASE_URL = "https://nominatim.openstreetmap.org/"

        private  val retrofitFactory =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        fun getOSMService(): PasService {
            return retrofitFactory.create(PasService::class.java)
        }

}