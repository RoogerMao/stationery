package com.example.stationery.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.stationery.R
import com.example.stationery.logic.model.UserSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(
    userSettingsViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val userSettingsUIState by userSettingsViewModel.userSettingsUIState.collectAsState()
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        UserProfile(
            userSettingsViewModel = userSettingsViewModel,
            username = userSettingsUIState.username
        )
        UserSettingsSwitches(
            displaySummaries = userSettingsUIState.displaySummaries,
            toggleDisplaySummaries = { userSettingsViewModel.toggleSummaryDisplay() },
            displayInsights = userSettingsUIState.displayInsights,
            toggleDisplayInsights = { userSettingsViewModel.toggleInsightDisplay() }
        )
    }

    if(userSettingsViewModel.showEditUsernameDialog) {
        Dialog(
            onDismissRequest = { userSettingsViewModel.onDismissEditUsernameDialog() }
        ) {
            Card {
                Column {
                    TextField(
                        value = userSettingsUIState.newUsername,
                        onValueChange = { userSettingsViewModel.editNewUsername(it) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.edit_username)
                            )
                        }
                    )
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            onClick = { userSettingsViewModel.onDismissEditUsernameDialog() },
                            content = {
                                Text(text = "Dismiss")
                            }
                        )
                        Button(
                            onClick = { userSettingsViewModel.onConfirmEditUsernameDialog(userSettingsUIState.newUsername) },
                            content = {
                                Text(text = "Confirm")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfile(
    userSettingsViewModel: UserSettingsViewModel,
    username: String
) {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val defaultImageUri = Uri.parse("android.resource://${context.packageName}/${R.drawable.default_profile_photo}")

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            item {
                AsyncImage(
                    model = selectedImageUri ?: defaultImageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button (
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    userSettingsViewModel.updateProfileImage(newImageId = selectedImageUri)
                },
            ) {
                Text(
                    text = "Change Profile Photo",
                    textAlign = TextAlign.Center
                )
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = username,
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
