package br.senai.sp.jandira.tcc_pas.service

import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.model.DisponibilidadeResponse
import br.senai.sp.jandira.tcc_pas.model.EspecialidadeResponse
import br.senai.sp.jandira.tcc_pas.model.FiltrarUnidadesResponse
import br.senai.sp.jandira.tcc_pas.model.Filtros
import retrofit2.http.Body
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeResponse
import br.senai.sp.jandira.tcc_pas.model.NominatimAddressItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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



    // Open Street Map

    @GET("search")
    suspend fun buscarPorCep(
        @Query("postalcode") cep: String,

        // já pré-definido
        @Query("countrycodes") countryCodes: String = "BR",
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1,
        @Query("addressdetails") addressDetails: Int = 1
    ): Response<List<NominatimAddressItem>>

//    https://nominatim.openstreetmap.org/reverse?lat=-23.52864&lon=-46.89797&format=json&addressdetails=1
}

