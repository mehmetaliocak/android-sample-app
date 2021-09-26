package com.adesso.movee.scene.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.R

@Composable
fun LoginScene(
    usernameLiveData: LiveData<String>,
    passwordLiveData: LiveData<String>,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {

    val username by usernameLiveData.observeAsState("")
    val password by passwordLiveData.observeAsState("")

    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.margin_xxl),
                    end = dimensionResource(id = R.dimen.margin_xxl)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_movee),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.margin_login_image_view_movee))
            )

            LoginTextField(
                value = username,
                onValueChange = onUsernameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.margin_login_image_view_movee)),
                label = stringResource(id = R.string.login_hint_username),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            PasswordTextField(password, onPasswordChange)

            LoginClickableText(
                value = stringResource(id = R.string.login_message_forgot_password),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.margin_large))
                    .align(alignment = Alignment.End),
                onClick = onForgotPasswordClick
            )

            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.margin_xxl)),
                colors = buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.login_message_login),
                    color = colorResource(id = R.color.vibrant_blue),
                    fontSize = 17.sp
                )
            }

            LoginClickableText(
                value = stringResource(id = R.string.login_message_register),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.margin_xxl))
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = onRegisterClick
            )
        }
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val localFocusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(label, color = Color.White)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
            onDone = { localFocusManager.clearFocus() }
        ),
        colors = textFieldColors(
            cursorColor = Color.White,
            textColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Gray,
            backgroundColor = Color.Transparent
        ),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    onPasswordChanged: (String) -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    LoginTextField(
        value = password,
        onValueChange = onPasswordChanged,
        label = stringResource(R.string.login_hint_password),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },

        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                }
            ) {
                Icon(imageVector = image, null, tint = Color.White)
            }
        }
    )
}

@Composable
fun LoginClickableText(
    value: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    onClick: () -> Unit = {},
) {
    Text(
        text = value,
        color = Color.White,
        fontSize = 12.sp,
        modifier = modifier.clickable { onClick() },
        textAlign = textAlign
    )
}

@Preview
@Composable
fun LoginPreview() {
    LoginScene(
        MutableLiveData(),
        MutableLiveData(),
        {},
        {},
        {},
        {},
        {}
    )
}
