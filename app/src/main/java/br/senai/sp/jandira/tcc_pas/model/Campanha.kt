package br.senai.sp.jandira.tcc_pas.model


data class CampanhaResponse(
    val id: Int,
    val nome: String  = "",
    val descricao: String  = "",
    val data_inicio: String  = "",
    val data_fim: String  = ""
)