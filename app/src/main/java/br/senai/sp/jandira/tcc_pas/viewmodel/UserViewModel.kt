package br.senai.sp.jandira.tcc_pas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse


//define a forma de dados que vai chegar, e cria funcao para login e deslogar
open class UserViewModel : ViewModel() {
    var userData by mutableStateOf<LoginResponse?>(null)

    //logar
    fun setUser(user: LoginResponse) {
        userData = user
    }

    //deslogar
    fun clearUser() {
        userData = null
    }
}
