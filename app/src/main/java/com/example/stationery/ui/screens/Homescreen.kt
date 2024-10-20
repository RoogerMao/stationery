package com.example.stationery.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.stationery.R
import com.example.stationery.data.StickyDetails
import com.example.stationery.data.StickyUIState
import com.example.stationery.logic.model.StickyViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

@Composable
fun HomeScreen(
    stickyViewModel: StickyViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn {  }
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = stringResource(id = R.string.add_sticky),
            modifier = Modifier.clickable {
                stickyViewModel.onShowEditStickyDialog()
            }
        )
    }

    if(stickyViewModel.showStickyEditScreen) {
        EditStickyDialog(
            stickyFlow = stickyViewModel.stickyUIState,
            showDatePicker = stickyViewModel.showStickyDatePicker,
            onToggleDatePicker = { stickyViewModel.onToggleStickyDatePicker() },
            onValueChange = { stickyViewModel.updateSticky(it) },
            onDismiss = { stickyViewModel.onDismissEditStickyDialog() },
            onSave = {
                coroutineScope.launch {
                    stickyViewModel.saveSticky()
                }
                stickyViewModel.onDismissEditStickyDialog()
            },
            modifier = Modifier.focusable()
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditStickyDialog(
    stickyFlow: StateFlow<StickyUIState>,
    showDatePicker: Boolean,
    onToggleDatePicker: () -> Unit,
    onValueChange: (StickyDetails) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier.focusable()
) {
    val stickyUIState by stickyFlow.collectAsState(initial = StickyUIState())
    val datePickerState = rememberDatePickerState()
    val focusManager= LocalFocusManager.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextField(
                    value = stickyUIState.stickyDetails.title,
                    onValueChange = {
                        onValueChange(stickyUIState.stickyDetails.copy(title = it)) },
                    label = {
                        Text(
                            text = "Activity Name"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                )

                // expand the dialogue to include a calendar, add animations later
                StickySetting(
                    painterResourceID = R.drawable.baseline_calendar_today_24,
                    settingNameResourceID = R.string.sticky_date,
                    settingValue = stickyUIState.stickyDetails.date,
                    onSettingValueClicked = {
                        // save current datePicker information
                        val dateSelected = Date(
                            datePickerState.selectedDateMillis ?:
                            LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()
                        )

                        onValueChange(
                            stickyUIState.stickyDetails.copy(
                                date = LocalDateTime.ofInstant(dateSelected.toInstant(), ZoneId.of("UTC")).format(
                                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                )
                            )
                        )

                        //show date picker
                        onToggleDatePicker()
                    }
                )

                // show date picker
                if(showDatePicker) {
                    DatePicker(
                        state = datePickerState
                    )
                }

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
                    onSettingValueClicked = { onToggleDatePicker() }
                )
                TextField(
                    value = stickyUIState.stickyDetails.timeCommitted,
                    onValueChange = {
                        onValueChange(stickyUIState.stickyDetails.copy(timeCommitted = it))
                    },
                    label = {
                        Text(
                            text = "Time Committed (Hours)"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(8.dp)
                )
                Row {
                    TextButton(
                        onClick = onDismiss,
                        content = {
                            Text(text = "Dismiss")
                        },
                        modifier = Modifier.weight(1F)
                    )
                    TextButton(
                        onClick = onSave,
                        content = { Text(text = "Save") },
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
    onSettingValueClicked: () -> Unit,                       // what to display
    modifier: Modifier = Modifier
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
        Text( // make this bolded, do that with material theming later
            text = settingName
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(
            text = settingValue, 
            modifier = Modifier.clickable (
                onClick = onSettingValueClicked
            )
        )
    }
}