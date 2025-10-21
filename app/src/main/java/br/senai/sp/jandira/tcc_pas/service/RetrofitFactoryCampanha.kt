package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryCampanha {

    private val BASE_URL = "https://api-fake-de-campanhas-com-json-server-4.onrender.com/"

    private  val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun getCampanhaService(): PasService {
        return retrofitFactory.create(PasService::class.java)
    }
}