package br.senai.sp.jandira.tcc_pas.service

import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.model.DisponibilidadeResponse
import br.senai.sp.jandira.tcc_pas.model.EspecialidadeResponse
import br.senai.sp.jandira.tcc_pas.model.FiltrarUnidadesResponse
import br.senai.sp.jandira.tcc_pas.model.Filtros
import retrofit2.http.Body
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import br.senai.sp.jandira.tcc_pas.model.PesquisaResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeResponse
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

    @GET("v1/pas/especialidade")
    suspend fun listarEspecialidade(): Response<EspecialidadeResponse>

    @GET("v1/pas/categoria")
    suspend fun listarUnidades(): Response<UnidadeResponse>


    @GET("v1/pas/unidades")
    suspend fun listarDisponibilidade(): Response<DisponibilidadeResponse>

    @POST("v1/pas/unidades/filtrar")
    suspend fun filtrarUnidades(@Body filtros: Filtros): Response<FiltrarUnidadesResponse>

    @GET("v1/pas/pesquisa/{termo}")
    suspend fun pesquisar(@Path("termo") termo: String): Response<PesquisaResponse>

}

