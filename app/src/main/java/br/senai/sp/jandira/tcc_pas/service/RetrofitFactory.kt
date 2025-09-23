package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL = "http://10.107.140.24:3000"

    private  val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun getPasService(): PasService{
        return retrofitFactory.create(PasService::class.java)
    }
}