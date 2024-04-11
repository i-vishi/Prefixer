package dev.vishalgaur.prefixer.ui.prefEdit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dev.vishalgaur.prefixer.base.PrefValueType
import dev.vishalgaur.prefixer.core.state.TextFieldState
import dev.vishalgaur.prefixer.core.ui.PrefixerTextField
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme
import dev.vishalgaur.prefixer.ui.theme.StringValueColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SharedPreferencesEditBottomSheet(
    modifier: Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    key: String,
    prefValue: PrefValueType,
    onSubmit: (value: String) -> Unit,
    onCancel: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valueFieldState = remember { TextFieldState(initialValue = prefValue.value.toString()) }
    val booleanValueState = remember {
        mutableStateOf(
            if (prefValue is PrefValueType.BooleanType) prefValue.value else false,
        )
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        onDismissRequest = {
            onCancel()
        },
    ) {
        Text(
            text = "Update Shared Preferences",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Key:",
                modifier = Modifier.wrapContentSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = key,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Value:",
                modifier = Modifier.wrapContentSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = StringValueColor,
            )
            PrefValueEditView(
                modifier = Modifier,
                prefValue = prefValue,
                valueFieldState = valueFieldState,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                onEditBoolean = {
                    booleanValueState.value = it
                },
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = { onCancel() }) {
                Text(text = "Cancel")
            }

            Button(modifier = Modifier.weight(1f), onClick = { onSubmit("") }) {
                Text(text = "Save")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RowScope.PrefValueEditView(
    modifier: Modifier,
    prefValue: PrefValueType,
    valueFieldState: TextFieldState,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onEditBoolean: (Boolean) -> Unit,
) {
    when (prefValue) {
        is PrefValueType.BooleanType -> {
            var checkState by remember { mutableStateOf(prefValue.value) }
            Checkbox(
                modifier = modifier,
                checked = checkState,
                onCheckedChange = {
                    checkState = it
                    onEditBoolean(it)
                },
            )
        }

        is PrefValueType.StringType, is PrefValueType.IntType -> {
            PrefixerTextField(
                modifier = modifier
                    .focusRequester(focusRequester)
                    .weight(1f),
                textFieldState = valueFieldState,
                validator = {
                    if (prefValue is PrefValueType.IntType) it.isDigitsOnly() else true
                },
                placeholder = {},
                maxCharacters = 50,
                keyboardType = if (prefValue is PrefValueType.IntType) KeyboardType.Number else KeyboardType.Text,
                textStyle = MaterialTheme.typography.bodyLarge,
                imeAction = ImeAction.Done,
                onImeAction = {
                    keyboardController?.hide()
                },
                maxLines = 4,
                minLines = 1,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun PreviewBottomSheet() {
    PrefixerTheme {
        SharedPreferencesEditBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(),
            key = "key1",
            prefValue = PrefValueType.StringType("some value"),
            onSubmit = {},
            onCancel = {},
        )
    }
}
