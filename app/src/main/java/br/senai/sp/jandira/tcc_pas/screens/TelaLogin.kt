package br.senai.sp.jandira.tcc_pas.screens

import android.app.AlertDialog
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.Login
import kotlinx.coroutines.launch


// ESSAS TRATATIZA DE QUANTIDADE VAI SAIR, POIS NO BACK JA VAI TER,
// ENTAO NO MOBILE VAI FICAR APENAS A AUTOMACAO DE COLOCAR . E - SEM O USUARIO PRECISAR COLOCAR


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
fun TelaLogin() {
    val cpf = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Estado para AlertDialogs
    var mostrarTelaSucesso by remember { mutableStateOf(false) }
    var mostrarTelaErro by remember { mutableStateOf(false) }

    // VAI SAIR ESSE DADOS FAKES E ENTRAR A API DO GOV
    // Função fake de validação
    fun validarLoginFake(cpf: String, senha: String): Boolean {
        return cpf == "12345678901" && senha == "123456" // exemplo fixo
    }

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
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
                Text(
                    text = stringResource(R.string.login_gov),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = cpf.value,
                    onValueChange = { input ->
                        cpf.value = input.filter { it.isDigit() }.take(11)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.digite_cpf),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiaryContainer
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
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.senha),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiaryContainer
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

                Button(
                    onClick = {
                        if (validarLoginFake(cpf.value, senha.value)) {
                            mostrarTelaSucesso = true
                        } else {
                            mostrarTelaErro = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,   // fundo
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer     // texto
                    )
                ) {
                    Text(text = "Entrar")
                }


                Spacer(modifier = Modifier.height(40.dp))


                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.nao_tem_conta),
                        color = Color(0xFF982829),
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
                            contentColor = Color(0xFF982829)
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.criar_conta),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // AlertDialogs
                if (mostrarTelaSucesso) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text(text = "Sucesso") },
                        text = { Text(text = "Login efetuado com sucesso!") },
                        confirmButton = {
                            Button(onClick = { mostrarTelaSucesso = false }) {
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                if (mostrarTelaErro) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text(text = "Erro") },
                        text = { Text(text = "CPF ou senha inválidos!") },
                        confirmButton = {
                            Button(onClick = { mostrarTelaErro = false }) {
                                Text(text = "Ok")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TelaLoginPreview() {
    TelaLogin()
}





