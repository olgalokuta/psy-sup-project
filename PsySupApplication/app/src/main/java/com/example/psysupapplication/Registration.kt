package com.example.psysupapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(stay : MutableState<Boolean>) : Unit {
    var password by rememberSaveable { mutableStateOf("") }
    var username by remember { mutableStateOf(TextFieldValue(""))}
    var email by remember { mutableStateOf(TextFieldValue(""))}
    var phone by remember { mutableStateOf(TextFieldValue(""))}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.7f),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Пройдите регистрацию, для того чтобы начать пользоваться приложением",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                )
            }
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = "Придумайте никнейм") },
                placeholder = { Text(text = "Введите никнейм") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Придумайте пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text(text = "Ваша почта") },
                placeholder = { Text(text = "Введите почту") }
            )
            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                label = { Text(text = "Ваш номер телефона") },
                placeholder = { Text(text = "Введите номер телефона") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = {
                    stay.value = true
                },
                modifier = Modifier.fillMaxWidth(fraction = 0.7f)
            ) {
                Text(
                    text = "Войти",
                    fontSize = 20.sp
                )
            }
        }
    }
}