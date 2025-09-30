package br.senai.sp.jandira.tcc_pas.model


//data class CampanhaResponse(
//    val id: Int,
//    val nome: String  = "",
//    val descricao: String  = "",
//    val data_inicio: String  = "",
//    val data_termino: String  = "",
//    val foto:  String = "",
//    val publico_alvo: String = "",
//    val tipo: String = "",
//    val tipo_unidade_disponivel: String = "",
//    val dias_horario: String = "",
//    val observacoes: String = "",
//    val cidade: String = "",
//    val unidades_disponiveis: String = ""
//
//)

data class CampanhaResponse(
    val id: Int,
    val nome: String = "",
    val descricao: String = "",
    val data_inicio: String = "",
    val data_termino: String = "",
    val foto: String = "",
    val publico_alvo: String = "",
    val tipo: String = "",
    val tipo_unidade_disponivel: String = "",
    val dias_horario: String = "",
    val observacoes: String = "",
    val cidades: List<CidadeItem> = emptyList()
)

data class CidadeItem(
    val cidade: String = "",
    val unidades_disponiveis: List<String> = emptyList()
)
