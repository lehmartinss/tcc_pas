package br.senai.sp.jandira.tcc_pas.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactory
import kotlinx.coroutines.launch

open class UserViewModel : ViewModel() {
    var userData by mutableStateOf<LoginResponse?>(null)

    private val api = RetrofitFactory().getPasService()

    // logar
    fun setUser(user: LoginResponse) {
        userData = user
    }

    // deslogar
    fun clearUser() {
        userData = null
    }

    fun atualizarUsuario(usuarioAtualizado: LoginResponse) {
        viewModelScope.launch {
            try {
                val usuarioComFoto = usuarioAtualizado.copy(
                    foto_perfil = usuarioAtualizado.foto_perfil ?: userData?.foto_perfil
                )

                val id = usuarioComFoto.id
                val response = api.atualizarUsuario(id, usuarioComFoto)

                if (response.isSuccessful) {
                    val dadosAtualizados = response.body()

                    userData = userData?.copy(
                        nome = dadosAtualizados?.nome ?: userData?.nome.orEmpty(),
                        cpf = dadosAtualizados?.cpf ?: userData?.cpf.orEmpty(),
                        naturalidade = dadosAtualizados?.naturalidade ?: userData?.naturalidade.orEmpty(),
                        nascimento = dadosAtualizados?.nascimento ?: userData?.nascimento.orEmpty(),
                        nome_mae = dadosAtualizados?.nome_mae ?: userData?.nome_mae.orEmpty(),
                        email = dadosAtualizados?.email ?: userData?.email.orEmpty(),
                        cep = dadosAtualizados?.cep ?: userData?.cep.orEmpty(),
                        telefone = dadosAtualizados?.telefone ?: userData?.telefone.orEmpty(),
                        senha = dadosAtualizados?.senha ?: userData?.senha.orEmpty(),
                        foto_perfil = dadosAtualizados?.foto_perfil ?: userData?.foto_perfil
                    )

                    Log.i("UserViewModel", "Usuário atualizado com sucesso!")
                } else {
                    Log.e("UserViewModel", "Erro ao atualizar: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("UserViewModel", "Falha ao atualizar usuário: ${e.message}")
            }
        }
    }
}
