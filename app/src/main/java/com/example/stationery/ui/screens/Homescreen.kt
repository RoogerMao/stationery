package com.example.stationery.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stationery.R
import com.example.stationery.logic.model.StickyViewModel

@Composable
fun HomeScreen(
    stickyViewModel: StickyViewModel = viewModel()
) {

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn {  }
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = stringResource(id = R.string.add_sticky),
            modifier = Modifier.clickable {

            }
        )
    }

    if(stickyViewModel.showStickyEditScreen) {
        EditStickyDialog(/*stickyViewModel*/)
    }
}

@Composable
fun EditStickyDialog(
    //stickyViewModel: StickyViewModel = viewModel()
) {
    Dialog(
        onDismissRequest = { /*stickyViewModel.onDismissEditStickyDialog()*/ }
    ) {
        Card {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(
                            text = "Name"
                        )
                    },
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                )
                StickySetting(
                    painterResourceID = R.drawable.baseline_calendar_today_24,
                    settingNameResourceID = R.string.sticky_date,
                    settingValue = "01/01/24",
                    onSettingValueClicked = {}
                )
                StickySetting(
                    painterResourceID = R.drawable.baseline_school_24,
                    settingNameResourceID = R.string.sticky_career_field,
                    settingValue = "Sample Field",
                    onSettingValueClicked = {}
                )
                StickySetting(
                    painterResourceID = R.drawable.baseline_category_24,
                    settingNameResourceID = R.string.sticky_type,
                    settingValue = "Research",
                    onSettingValueClicked = {}
                )
                StickySetting(
                    painterResourceID = R.drawable.baseline_thumb_up_24,
                    settingNameResourceID = R.string.sticky_interest,
                    settingValue = "Medium",
                    onSettingValueClicked = {}
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(
                            text = "Time Committed"
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )
                Row (
                ) {
                    TextButton(
                        onClick = {},
                        content = {
                            Text(text = "Dismiss")
                        },
                        modifier = Modifier.weight(1F)
                    )
                    TextButton(
                        onClick = {},
                        content = {
                            Text(text = "Confirm")
                        },
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
    }
}

@Composable
fun StickySetting(
    painterResourceID: Int,
    settingNameResourceID: Int,
    settingValue: String,
    onSettingValueClicked: () -> Unit                       // what to display
) {
    val settingName = stringResource(id = settingNameResourceID)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ){
        Icon(
            painter = painterResource(painterResourceID),
            contentDescription = "Edit $settingName"
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text( // make this bolded
            text = settingName
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(
            text = settingValue, 
            modifier = Modifier.clickable {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StickyDialogPreview() {
    EditStickyDialog()
}
