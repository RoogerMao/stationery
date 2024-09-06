package com.example.stationery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserSettingsScreen() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        UserProfile()
        UserSettingsSwitches()
    }
}

@Composable
fun UserProfile() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.default_profile_photo),
                contentDescription = null
            )
            Button(
                onClick = {},
                content = {

                }
            )
        }
        Text(
            text = stringResource(id = R.string.default_username),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SwitchSetting(label: String) {
    Row (
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label
        )
        Switch(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
fun UserSettingsSwitches() {
    Column {
        SwitchSetting(stringResource(id = R.string.toggle_insights_label))
        SwitchSetting(stringResource(id = R.string.toggle_summaries_label))
    }
}

@Preview(showBackground = true)
@Composable
fun UserSettingsPreview() {
    UserSettingsScreen()
}
