package dev.vishalgaur.prefixer.ui.allPreferences

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishalgaur.prefixer.base.PrefValueType
import dev.vishalgaur.prefixer.ui.models.PreferencesPair
import dev.vishalgaur.prefixer.ui.prefEdit.SharedPreferencesEditBottomSheet
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun AllPreferencesScreen(prefsName: String, prefsList: List<PreferencesPair>, onUpdatePref: (PreferencesPair) -> Unit) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val (selectedPref, setSelectedPref) = remember {
        mutableStateOf<PreferencesPair?>(null)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                AllPreferencesTopAppBar(modifier = Modifier.fillMaxWidth())
                Divider(modifier = Modifier.shadow(4.dp))
            }
        },
    ) {
        if (showBottomSheet && selectedPref != null) {
            SharedPreferencesEditBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
                key = selectedPref.key,
                prefValue = selectedPref.value,
                onSubmit = {
                    coroutineScope.launch {
                        onUpdatePref(PreferencesPair(selectedPref.key, it))
                        sheetState.hide()
                    }.invokeOnCompletion {
                        setSelectedPref(null)
                        showBottomSheet = false
                    }
                },
                onCancel = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        setSelectedPref(null)
                        showBottomSheet = false
                    }
                },
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = listState,
        ) {
            stickyHeader {
                PrefsNameStickyHeader(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 10.dp),
                    prefsName = prefsName,
                )
            }
            prefsList.forEachIndexed { index, prefItem ->
                item(key = prefItem.key) {
                    PreferencesPairItemView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onLongClick = {
                                    setSelectedPref(prefItem)
                                    showBottomSheet = true
                                },
                                onClick = {},
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        key = prefItem.key,
                        prefValue = prefItem.value,
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Composable
private fun PrefsNameStickyHeader(modifier: Modifier, prefsName: String) {
    Row(
        modifier = modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(text = "Prefs Name: ", modifier = Modifier.padding(end = 4.dp))
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(4.dp)
                .weight(1f),
            text = prefsName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview("Light Theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAllPreferencesScreen() {
    PrefixerTheme {
        AllPreferencesScreen(
            prefsName = "app_prefs",
            prefsList = listOf(
                PreferencesPair("dsf", PrefValueType.LongType(4354)),
                PreferencesPair("dsfd", PrefValueType.BooleanType(false)),
                PreferencesPair(
                    "dsfdfsgdd",
                    PrefValueType.StringType("dsfg dlif ksepodr jfg eprdo ;fjgewg dlif ksepodr jfg eprdo ;fjgewg dlif ksepodr jfg eprdo ;fjgewwsfg dlif ksepodr jfg eprdo ;fjgewreabdgh"),
                ),
            ),
            onUpdatePref = {},
        )
    }
}
