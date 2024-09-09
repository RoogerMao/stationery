package com.example.stationery

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserSettingsScreen(
    userSettingsViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier
) {
    val userSettingsUIState by userSettingsViewModel.userSettingsUIState.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        UserProfile(userSettingsViewModel)
        UserSettingsSwitches(
            displaySummaries = userSettingsUIState.displaySummaries,
            toggleDisplaySummaries = { userSettingsViewModel.toggleSummaryDisplay() },
            displayInsights = userSettingsUIState.displayInsights,
            toggleDisplayInsights = { userSettingsViewModel.toggleInsightDisplay() }
        )
    }
}

@Composable
fun UserProfile(
    userSettingsViewModel: UserSettingsViewModel
) {
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
        Row {
            Text(
                text = stringResource(id = R.string.default_username),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {},
                content = {
                }
            )
        }
    }
}

@Composable
fun SwitchSetting(
    label: String,
    value: Boolean,
    toggleFunction: () -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label
        )
        Switch(
            checked = value,
            onCheckedChange = { toggleFunction() }
        )
        Log.d(
            "SWITCH",
            label + " = $value"
        )
    }
}

@Composable
fun UserSettingsSwitches(
    displayInsights: Boolean,
    toggleDisplayInsights: () -> Unit,
    displaySummaries: Boolean,
    toggleDisplaySummaries: () -> Unit
) {
    Column {
        SwitchSetting(
            label = stringResource(id = R.string.toggle_insights_label),
            value = displayInsights,
            toggleFunction =  { toggleDisplayInsights() }
        )
        SwitchSetting(
            label = stringResource(id = R.string.toggle_summaries_label),
            value = displaySummaries,
            toggleFunction = { toggleDisplaySummaries() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserSettingsPreview() {
    //UserSettingsScreen()
}
