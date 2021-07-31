package com.example.animelist.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.animelist.R
import com.example.animelist.ui.theme.RobotoCondensed

@Composable
fun LoginScreen(
    navController : NavController
){
    /* Function for handle the status bar */
    SettingStatusBar()
    FormView(navController = navController)
}

@Composable
fun SettingStatusBar(){
    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = MaterialTheme.colors.isLight

    /* Change status bar to invisible */
    SideEffect {
        systemUiController.isStatusBarVisible =  false
    }
}

@Composable
fun FormView(
    navController: NavController,
){
    Column {
        var usernameValueState by remember {
            mutableStateOf("")
        }
        var passwordValueState by remember {
            mutableStateOf("")
        }

        /* Username Side */

        Text(
            text = stringResource(id = R.string.username_text),
            fontSize = 18.sp,
            fontFamily = RobotoCondensed,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
        ) {
            TextField(
                value = usernameValueState,
                onValueChange = { usernameValueState = it }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        /* Password Side */

        Text(
            text = stringResource(id = R.string.password_text),
            fontSize = 18.sp,
            fontFamily = RobotoCondensed,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
        ) {
            TextField(
                value = passwordValueState,
                onValueChange = { passwordValueState = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
        }
    }
}