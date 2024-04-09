package dev.vishalgaur.prefixer.ui.prefEdit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme
import dev.vishalgaur.prefixer.ui.theme.StringValueColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedPreferencesEditBottomSheet(
    modifier: Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    key: String,
    value: String,
    onSubmit: (value: String) -> Unit,
    onCancel: () -> Unit,
) {
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
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Key:",
                modifier = Modifier.wrapContentSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "$key",
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color(0xFFCCCCCC),
                        shape = RoundedCornerShape(4.dp),
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Value:",
                modifier = Modifier.wrapContentSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = StringValueColor,
            )
            Text(
                text = "$value",
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = StringValueColor,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = StringValueColor,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun PreviewBottomSheet() {
    PrefixerTheme {
        SharedPreferencesEditBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(),
            key = "key1",
            value = "some value",
            onSubmit = {},
            onCancel = {},
        )
    }
}
