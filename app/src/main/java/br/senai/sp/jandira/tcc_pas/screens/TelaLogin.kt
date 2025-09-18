package br.senai.sp.jandira.tcc_pas.screens

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.Login
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



// FAZ A FORMATACAO DOS NUMEROS DIGITADO PELO USUARIO SER UM CPF
class CpfVisualTransformation : VisualTransformation {
    override fun filter(texto: AnnotatedString): TransformedText {
        val numeros = texto.text.filter { it.isDigit() }.take(11)
        val formatado = buildString {
            numeros.forEachIndexed { indice, char ->
                when (indice) {
                    3, 6 -> append('.')
                    9 -> append('-')
                }
                append(char)
            }
        }

        val mapeamentoOffset = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var novoOffset = offset
                if (offset > 2) novoOffset += 1
                if (offset > 5) novoOffset += 1
                if (offset > 8) novoOffset += 1
                return novoOffset.coerceAtMost(formatado.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var novoOffset = offset
                if (offset > 3) novoOffset -= 1
                if (offset > 7) novoOffset -= 1
                if (offset > 11) novoOffset -= 1
                return novoOffset.coerceAtMost(numeros.length)
            }
        }

        return TransformedText(AnnotatedString(formatado), mapeamentoOffset)
    }
}


@Composable
fun TelaLogin(navController: NavHostController?) {
    var cpf = remember { mutableStateOf("") }
    var senha = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Estado para AlertDialogs e Aceitou os Termos
    var mostrarTelaSucesso by remember { mutableStateOf(false) }
    var mostrarTelaErro by remember { mutableStateOf(false) }
    var mostrarErroTermos by remember { mutableStateOf(false) }
    var aceitouTermos by remember { mutableStateOf(false) }

    fun validarLogin(cpf: String, senha: String): Boolean {
        val cpfValido = cpf.length == 11 // apenas números
        val senhaValida = senha.isNotEmpty()

        var status = false
        if(cpfValido && senhaValida){
            status = true
        }

        return status
    }

    // CRIAR UMA INSTANCIA DO RETROFITFACTORY
    val apiGov = RetrofitFactory().getPasService()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo_description),
                    modifier = Modifier.size(199.dp)
                )
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E5FA3)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.login_gov),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E5FA3)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Image(
                        painter = painterResource(id = R.drawable.gov),
                        contentDescription = "gov.br logo",
                        modifier = Modifier
                            .height(16.dp)
                            .wrapContentWidth()
                    )
                }

//                Text(
//                    text = stringResource(R.string.login_gov),
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color(0xFF1E5FA3)
//                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = cpf.value,
                    onValueChange = { input ->
                        cpf.value = input.filter { it.isDigit() }.take(11)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.digite_cpf),
                            color = Color(0xFF9BA6AF),
                            fontSize = 15.sp,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint =Color(0xFF1E5FA3),
                            modifier = Modifier.size(23.dp)
                        )
                    },
                    visualTransformation = CpfVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = senha.value,
                    onValueChange = { senha.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.senha),
                            color = Color(0xFF9BA6AF),
                            fontSize = 15.sp,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFF1E5FA3),
                            modifier = Modifier.size(23.dp) 
                        )
                    },

                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = aceitouTermos,
                        onClick = { aceitouTermos = !aceitouTermos },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF7FBEF8)
                        ),

                   )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.li_aceito),
                            fontSize = 10.sp,
                            color = Color(0xFF9BA6AF)
                            )
                        Spacer(modifier = Modifier.width(3.dp))

                        TextButton(
                            onClick = {
                                // navegação para termos, ex:
                                // navController?.navigate("tela_termos")
                            },
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.termos_uso),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7FBEF8)

                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (!aceitouTermos) {
                            mostrarErroTermos = true
                        } else if (validarLogin(cpf.value, senha.value)) {
                            mostrarTelaErro = false
                            val login = Login(
                                cpf = cpf.value,
                                senha = senha.value
                            )

                            GlobalScope.launch(Dispatchers.IO) {
                                val response = apiGov.inserir(login)
                                if (response.isSuccessful) {
                                    val body = response.body()
                                    if (body != null) {
                                        mostrarTelaSucesso = true
                                    } else {
                                        mostrarTelaErro = true
                                    }
                                } else {
                                    mostrarTelaErro = true
                                }
                            }

                        } else {
                            mostrarTelaErro = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(top = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF1E5FA3),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text(text = stringResource(R.string.entrar))
                }

                // AlertDialogs
                if (mostrarTelaSucesso) {
                    AlertDialog(
                        onDismissRequest = { mostrarTelaSucesso = false },
                        title = { Text(text = "Sucesso") },
                        text = { Text(text = "Login efetuado com sucesso!") },
                        confirmButton = {
                            Button(onClick = { mostrarTelaSucesso = false
                                navController?.navigate("home")
                            }) {
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                if (mostrarTelaErro) {
                    AlertDialog(
                        onDismissRequest = { mostrarTelaErro = false },
                        title = { Text(text = "Erro") },
                        text = { Text(text = "CPF ou senha inválidos!") },
                        confirmButton = {
                            Button(onClick = { mostrarTelaErro = false
                                navController?.navigate("login")
                            }) {
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                if (mostrarErroTermos) {
                    AlertDialog(
                        onDismissRequest = { mostrarErroTermos = false },
                        title = { Text(text = "Termos não aceitos") },
                        text = { Text(text = "Você precisa ler e aceitar os termos para continuar.") },
                        confirmButton = {
                            Button(onClick = { mostrarErroTermos = false
                                navController?.navigate("login")
                            }) {
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                         navController?.navigate(route = "home")
                    },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF1E5FA3),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text(text = stringResource(R.string.continuar_sem_login))
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.nao_tem_conta),
                        color = Color(0xFF939AA4),
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    TextButton(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://sso.acesso.gov.br/login?client_id=portal-logado.estaleiro.serpro.gov.br&authorization_id=19952c0de59")
                            )
                            context.startActivity(intent)
                        },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF7FBEF8)

                        )
                    ) {
                        Text(
                            text = stringResource(R.string.criar_conta),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TelaLoginPreview() {
    TelaLogin(null)
}



