package br.senai.sp.jandira.tcc_pas.service

import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import retrofit2.Call
import retrofit2.http.Body
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PasService {

    @POST("login")
    suspend fun inserir(@Body login: Login): Response<LoginResponse>

    @GET("campanhas")
    suspend fun listarCampanhas(): Response<List<CampanhaResponse>>

    @GET("campanhas/{id}")
    suspend fun getCampanha(@Path("id") id: Int): Response<CampanhaResponse>

}