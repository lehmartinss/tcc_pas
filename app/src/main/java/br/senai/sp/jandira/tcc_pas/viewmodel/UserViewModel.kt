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


//define a forma de dados que vai chegar, e cria funcao para login e deslogar
//open class UserViewModel : ViewModel() {
//    var userData by mutableStateOf<LoginResponse?>(null)
//
//    //logar
//    fun setUser(user: LoginResponse) {
//        userData = user
//    }
//
//    //deslogar
//    fun clearUser() {
//        userData = null
//    }
//}



//open class UserViewModel : ViewModel() {
//    var userData by mutableStateOf<LoginResponse?>(null)
//
//    private val api = RetrofitFactory().getPasService() // ou o nome correto da sua factory
//
//    // logar
//    fun setUser(user: LoginResponse) {
//        userData = user
//    }
//
//    // deslogar
//    fun clearUser() {
//        userData = null
//    }
//
//    // ✅ atualizar dados do usuário
//    fun atualizarUsuario(usuarioAtualizado: LoginResponse) {
//        viewModelScope.launch {
//            try {
//                val id = usuarioAtualizado.id // ajuste se o campo for diferente
//                val response = api.atualizarUsuario(id, usuarioAtualizado)
//
//                if (response.isSuccessful) {
//                    userData = response.body()
//                    Log.i("UserViewModel", "Usuário atualizado com sucesso!")
//                } else {
//                    Log.e("UserViewModel", "Erro ao atualizar: ${response.errorBody()?.string()}")
//                }
//            } catch (e: Exception) {
//                Log.e("UserViewModel", "Falha ao atualizar usuário: ${e.message}")
//            }
//        }
//    }
//}


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

    // ✅ atualizar dados do usuário
    fun atualizarUsuario(usuarioAtualizado: LoginResponse) {
        viewModelScope.launch {
            try {
                // ✅ Garante que a foto antiga não seja perdida
                val usuarioComFoto = usuarioAtualizado.copy(
                    foto = usuarioAtualizado.foto ?: userData?.foto
                )

                val id = usuarioComFoto.id
                val response = api.atualizarUsuario(id, usuarioComFoto)

                if (response.isSuccessful) {
                    val dadosAtualizados = response.body()

                    // ✅ Mantém campos antigos se vierem nulos da API
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
                        foto = dadosAtualizados?.foto ?: userData?.foto // 👈 mantém a antiga se vier null
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
