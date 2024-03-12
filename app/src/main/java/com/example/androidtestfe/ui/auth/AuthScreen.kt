package com.example.androidtestfe.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.androidtestfe.R
import com.example.androidtestfe.data.localdatabase.model.ListModel
import com.example.androidtestfe.utils.Screen

@Composable
fun AuthScreen(navController: NavHostController, vm: AuthScreenViewModel = hiltViewModel()) {

    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(true) }
    var isLogin by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val uiState by vm.uiState.observeAsState()

    LaunchedEffect(uiState) {
        when (val data = uiState) {
            is UserUiState.Success -> {
                if (data.listModel.role == "User") {
                    navController.navigate(Screen.List.route)
                } else {
                    navController.navigate("${Screen.Admin.route}/${data.listModel.id}/${data.listModel.username}/${data.listModel.email}/${data.listModel.role}")
                }
                vm.uiState.value = UserUiState.None
            }

            is UserUiState.Fail -> {
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        ComposeLottie(modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(32.dp))

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.email),
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(id = R.string.password),
            leadingIcon = Icons.Default.Lock,
            isPassword = isPasswordVisible,
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) Icon(
                        painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                        contentDescription = null
                    ) else
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                            contentDescription = null
                        )
                }
            }
        )

        if (!isLogin) {

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = userName,
                onValueChange = { userName = it },
                label = stringResource(id = R.string.username),
                leadingIcon = Icons.Default.Face,
                isPassword = false,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(checked = checked, onCheckedChange = { checked = !checked })

                Text(text = "Admin", modifier = Modifier.padding(vertical = 10.dp))
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        AuthButton(
            text = if (isLogin) stringResource(id = R.string.login) else stringResource(id = R.string.register_instead),
            onClick = {
                if (!isLogin) {
                    vm.addUser(
                        model = ListModel(
                            username = userName,
                            email = email,
                            password = password,
                            role = if (checked) "Admin" else "User"
                        )
                    )
                    isLogin = !isLogin
                    password = ""
                } else {
                    vm.login(email = email, password = password)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        AuthToggle(
            isLogin = isLogin,
            onToggle = {
                isLogin = !isLogin
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = if (isPassword) ImeAction.Done else ImeAction.Next
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun AuthToggle(
    isLogin: Boolean,
    onToggle: () -> Unit
) {
    val text = if (isLogin) {
        stringResource(id = R.string.create_account)
    } else {
        stringResource(id = R.string.already_have_account)
    }

    val toggleText = if (isLogin) {
        stringResource(id = R.string.register_instead)
    } else {
        stringResource(id = R.string.login_instead)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )

        TextButton(onClick = onToggle) {
            Text(
                text = toggleText,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 15.dp)
            )
        }
    }
}

@Composable
fun ComposeLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logins))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true,
        isPlaying = true
    )

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    AuthScreen(navController)
}
