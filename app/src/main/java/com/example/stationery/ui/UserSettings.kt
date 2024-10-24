package com.example.stationery.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.stationery.R
import com.example.stationery.logic.model.UserSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(
    userSettingsViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val userSettingsUIState by userSettingsViewModel.userSettingsUIState.collectAsState()
    Box (
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            UserProfile(
                userSettingsViewModel = userSettingsViewModel,
                username = userSettingsUIState.username
            )
        }

        IconButton(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(64.dp)
                .width(64.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Return to Home Screen",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if(userSettingsViewModel.showEditUsernameDialog) {
        Dialog(
            onDismissRequest = { userSettingsViewModel.onDismissEditUsernameDialog() }
        ) {
            Card (
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    OutlinedTextField(
                        value = userSettingsUIState.newUsername,
                        onValueChange = { userSettingsViewModel.editNewUsername(it) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.edit_username),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
                    )
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        TextButton(
                            onClick = { userSettingsViewModel.onDismissEditUsernameDialog() },
                            content = {
                                Text(
                                    text = "Dismiss",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            modifier = Modifier.weight(1F)
                        )
                        TextButton(
                            onClick = { userSettingsViewModel.onConfirmEditUsernameDialog(userSettingsUIState.newUsername) },
                            content = {
                                Text(
                                    text = "Confirm",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            modifier = Modifier.weight(1F)
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

        Box {
            LazyColumn {
                item {
                    AsyncImage(
                        model = selectedImageUri ?: defaultImageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            IconButton(
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    userSettingsViewModel.updateProfileImage(newImageId = selectedImageUri)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .height(64.dp)
                    .width(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_image_24),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.width(48.dp)
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
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = stringResource(id = R.string.edit_username),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
                    .clickable(
                    onClick = {
                        userSettingsViewModel.onShowEditUsernameDialog()
                    }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserSettingsPreview() {
    UserSettingsScreen(navController = rememberNavController())
}
