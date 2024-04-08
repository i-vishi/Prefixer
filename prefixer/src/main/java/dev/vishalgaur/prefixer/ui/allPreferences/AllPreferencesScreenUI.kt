package dev.vishalgaur.prefixer.ui.allPreferences

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishalgaur.prefixer.ui.models.PreferencesPair
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AllPreferencesScreen(prefsName: String, prefsList: List<PreferencesPair>) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            ) {
                AllPreferencesTopAppBar(modifier = Modifier.fillMaxWidth())
                Divider(modifier = Modifier.shadow(4.dp))
            }
        },
    ) {
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
                            .clickable { }
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        key = prefItem.key,
                        value = prefItem.value,
                    )
                    Divider()
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
                PreferencesPair("dsf", "dsfdgh"),
                PreferencesPair("dsfd", "dsfwewreabdgh"),
                PreferencesPair("dsfdfsgdd", "dsfg dlif ksepodr jfg eprdo ;fjgewg dlif ksepodr jfg eprdo ;fjgewg dlif ksepodr jfg eprdo ;fjgewwsfg dlif ksepodr jfg eprdo ;fjgewreabdgh"),
            ),
        )
    }
}
