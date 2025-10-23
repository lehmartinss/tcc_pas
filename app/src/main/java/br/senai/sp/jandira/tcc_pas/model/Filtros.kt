package br.senai.sp.jandira.tcc_pas.model

import android.R

data class FiltrarUnidadesResponse(
    val status: Boolean,
    val status_code: Int,
    val item: Int,
    val unidadesDeSaude: List<UnidadeDeSaude>
)

data class Filtros(
    val categoria: String?,
    val especialidade: String?,
    val disponibilidade24h: Int? = null,
    val nomePesquisa: String? = null
)

data class UnidadeDeSaude(
    val id: Int,
    val nome: String,
    val telefone: String?,
    val disponibilidade_24h: Int,
    val foto: String?,
    val local: LocalWrapper,
    val categoria: CategoriaWrapper,
    val especialidades: EspecialidadesWrapper
)

data class UnidadeDeSaudeResponse(
    val id: Int,
    val nome: String,
    val telefone: String,
    val disponibilidade_24h: Int,
    val foto: String?,
    val local: LocalWrapper,
    val tempo_espera_geral: String,
    val especialidades: EspecialidadesWrapper? = null
)
data class LocalWrapper(
    val endereco: List<Endereco>
)

data class Endereco(
    val id: Int,
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val regiao: String
)

data class CategoriaWrapper(
    val categoria: List<Categoria>
)

data class Categoria(
    val id: Int,
    val nome: String,
    val foto_claro: String?,
    val foto_escuro: String?
)

data class EspecialidadesWrapper(
    val especialidades: List<EspecialidadeItem>
)

data class EspecialidadeItem(
    val id: Int,
    val nome: String,
    val foto_claro: String?,
    val foto_escuro: String?
)



data class Unidade(
    val id: Int,
    val nome: String,
    val foto_claro: String,
    val foto_escuro: String
)

data class UnidadeResponse(
    val status: Boolean,
    val status_code: Int,
    val item: Int,
    val categorias: List<Unidade>
)

data class UnidadeParaDisponibilidade(
    val id: Int,
    val nome: String,
    val telefone: String?,
    val disponibilidade_24h: Int
)

data class DisponibilidadeResponse(
    val status: Boolean,
    val status_code: Int,
    val item: Int,
    val unidadesDeSaude: List<UnidadeParaDisponibilidade>
)


data class Especialidade(
    val id: Int,
    val nome: String,
    val foto_claro: String,
    val foto_escuro: String,
//    val tempo_espera: String
)

data class EspecialidadeResponse(
    val status: Boolean,
    val status_code: Int,
    val item: Int,
    val especialidades: List<Especialidade>
)

data class ItemComFoto(
    val nome: String,
    val fotoClaro: String?
)

data class PesquisaResponse(
    val status: Boolean,
    val status_code: Int,
    val unidadesDeSaude: List<UnidadeDeSaude>
)
