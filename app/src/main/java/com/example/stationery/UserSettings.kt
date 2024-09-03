package com.example.stationery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserProfile() {
    Column() {
        Image(
            painter = painterResource(id = R.drawable.default_profile_photo),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.default_username)
        )
    }
}

@Composable
fun SwitchSetting() {
    Row {
        Text(
            text = "Sample Setting #1"
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
        SwitchSetting()
        SwitchSetting()
    }
}

@Preview(showBackground = true)
@Composable
fun UserSettingsPreview() {
    UserProfile()
}
