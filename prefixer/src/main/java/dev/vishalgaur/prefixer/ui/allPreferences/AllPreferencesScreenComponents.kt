package dev.vishalgaur.prefixer.ui.allPreferences

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishalgaur.prefixer.R
import dev.vishalgaur.prefixer.base.PrefValueType
import dev.vishalgaur.prefixer.ui.theme.BooleanColor
import dev.vishalgaur.prefixer.ui.theme.NumberColor
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme
import dev.vishalgaur.prefixer.ui.theme.StringValueColor
import java.math.MathContext

@Composable
internal fun PreferencesPairItemView(modifier: Modifier, key: String, prefValue: PrefValueType) {
    Row(
        modifier = modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            modifier = Modifier
                .padding(end = 8.dp)
                .widthIn(max = 120.dp)
                .wrapContentSize(),
            text = "\"${key}\" :",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )

        PreferenceValueView(prefValue)
    }
}

@Composable
private fun PreferenceValueView(prefValue: PrefValueType) {
    when (prefValue) {
        is PrefValueType.StringType -> {
            Text(
                text = "\"${prefValue.value}\"",
                style = MaterialTheme.typography.bodyMedium,
                color = StringValueColor,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
        }

        is PrefValueType.FloatType -> {
            Text(
                text = prefValue.value.toBigDecimal(mathContext = MathContext.DECIMAL128).toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = NumberColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        else -> {
            Text(
                text = "${prefValue.value}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (prefValue is PrefValueType.BooleanType) BooleanColor else NumberColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun AllPreferencesTopAppBar(modifier: Modifier) {
    Row(
        modifier = modifier
            .height(64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.title_activity_preferences_list),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview("Light Theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAllPreferencesScreenComponents() {
    PrefixerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AllPreferencesTopAppBar(Modifier.fillMaxWidth())
            PreferencesPairItemView(
                Modifier.fillMaxWidth(),
                "dwffbkurjsbfvosjbvksdjxbvoseg",
                PrefValueType.StringType("fsdgh"),
            )
            PreferencesPairItemView(Modifier.fillMaxWidth(), "dwfeg", PrefValueType.LongType(12))
            PreferencesPairItemView(
                Modifier.fillMaxWidth(),
                "dwfeg",
                PrefValueType.BooleanType(true),
            )
        }
    }
}
