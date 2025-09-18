package br.senai.sp.jandira.tcc_pas.model

data class Login(
//    val id: Long? = null,
    val cpf: String = "",
    val senha: String = ""
)

data class LoginResponse(
    val id: String = "",
    val nome: String = "",
    val cpf: String = "",
    val naturalidade: String = "",
    val nascimento: String = "",
    val nome_da_mae: String = "",
    val email: String = "",
    val cep: String = "",
    val telefone: String = "",
    val senha: String = ""
)
