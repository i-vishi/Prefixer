package dev.vishalgaur.prefixerapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishalgaur.prefixerapp.R
import dev.vishalgaur.prefixerapp.core.ui.TextFieldState

@Composable
fun SearchTextField(
    modifier: Modifier,
    textFieldState: TextFieldState = remember { TextFieldState() },
    placeholder: @Composable () -> Unit,
    infoText: String? = null,
    maxCharacters: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = textFieldState.text,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
                    textFieldState.text = it
                }
            },
            placeholder = placeholder,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    textFieldState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        textFieldState.enableShowErrors()
                    }
                },
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = textFieldState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = keyboardType,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                },
            ),
            trailingIcon = {
                if (textFieldState.getError() != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error_outline_24),
                        contentDescription = "Error Icon",
                        tint = Color.Red,
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
            ),
        )
        if (!infoText.isNullOrBlank()) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                text = infoText,
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 13.sp),
                color = Color.Red,
            )
        } else {
            Spacer(modifier = Modifier.padding(start = 16.dp, top = 2.dp).height(13.dp))
        }
    }
}
