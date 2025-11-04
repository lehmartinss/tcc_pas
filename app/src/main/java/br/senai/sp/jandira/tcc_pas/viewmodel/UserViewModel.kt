package br.senai.sp.jandira.tcc_pas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.model.LoginResponse

class UserViewModel : ViewModel() {
    var userData by mutableStateOf<LoginResponse?>(null)
        private set

    fun setUser(user: LoginResponse) {
        userData = user
    }

    fun clearUser() {
        userData = null
    }
}

// Exemplo de login bem-sucedido

//val user = User(id = 1, name = "João", email = "joao@email.com")
//userViewModel.setUser(user)
//
//@Composable
//fun ProfileScreen(userViewModel: UserViewModel = viewModel()) {
//    val user = userViewModel.userData
//
//    if (user != null) {
//        // Exibir dados do usuário
//        Text(text = "Nome: ${user.name}")
//        Text(text = "Email: ${user.email}")
//    } else {
//        // Caso os dados não estejam carregados
//        Text(text = "Carregando perfil...")
//    }
//}