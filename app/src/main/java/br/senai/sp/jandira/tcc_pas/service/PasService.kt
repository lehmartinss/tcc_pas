package br.senai.sp.jandira.tcc_pas.service

import retrofit2.Call
import retrofit2.http.Body
import br.senai.sp.jandira.tcc_pas.model.Login
import retrofit2.Response
import retrofit2.http.POST

interface PasService {

    @POST("usuarios")
    fun inserir(@Body login: Login): Response<Login>
}