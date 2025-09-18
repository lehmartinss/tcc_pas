package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Call
import retrofit2.http.Body
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import retrofit2.Response
import retrofit2.http.POST

interface PasService {

    @POST("login")
    suspend fun inserir(@Body login: Login): Response<LoginResponse>
}