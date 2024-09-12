package com.example.stationery

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserSettingsScreen(
    userSettingsViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier
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

    if(userSettingsViewModel.showEditUsernameDialog) {
        val editUsernameBuilder = AlertDialog.Builder(LocalContext.current)
        editUsernameBuilder
            .setTitle("Change username")
            // TO DO MAKE A VIEW TO GET TEXTFIELD, then use setView()

        val editUsernameDialog = editUsernameBuilder.create()
        editUsernameDialog.show()
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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = stringResource(id = R.string.default_username),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = stringResource(id = R.string.edit_username),
                tint = Color.Black,
                modifier = Modifier.clickable(
                    onClick = {
                        userSettingsViewModel.onShowEditUsernameDialog()
                    }
                )
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
    UserSettingsScreen()
}
