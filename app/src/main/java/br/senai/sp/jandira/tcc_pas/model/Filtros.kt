package br.senai.sp.jandira.tcc_pas.model

data class Filtros(
    val categoria: String,
    val especialidade: String
)


data class Categoria(
    val nome: String
)

data class Especialidade(
    val nome: String
)

data class UnidadeDeSaude(
    val disponibilidade: Int,
    val categoria: Categoria,
    val especialidade: Especialidade
)