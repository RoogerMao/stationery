package com.example.stationery.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import com.example.stationery.R
import com.example.stationery.data.Sticky
import com.example.stationery.data.StickyDetails
import com.example.stationery.data.StickyUIState
import com.example.stationery.data.toStickyDetails
import com.example.stationery.logic.model.SearchViewModel
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
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val stickiesUIState by searchViewModel.stickiesUIStateFlow.collectAsState()
    val stickySearchQuery by searchViewModel.stickySearchQuery.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row {
            OutlinedTextField(
                value = stickySearchQuery,
                onValueChange = { searchViewModel.updateStickySearchQuery(it) },
                modifier = Modifier
                    .weight(1F),
                placeholder = {
                    Text(
                        text = "Search sticky notes...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                              },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                textStyle = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AdvancedSearchChipRow(searchViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(items = stickiesUIState.stickyList) { stickyUIState ->
                    StickyCard(
                        stickyUIState.toStickyDetails()
                    )
                }
            }

            IconButton(
                onClick = { stickyViewModel.onShowEditStickyDialog() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .height(64.dp)
                    .width(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = stringResource(id = R.string.add_sticky),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            IconButton(
                onClick = {
                    navController.navigate("settings")
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
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    if(stickyViewModel.showStickyEditScreen) {
        EditStickyDialog(
            stickyFlow = stickyViewModel.stickyUIState,
            careerSearchTextFlow = stickyViewModel.careerSearchText,
            matchingCareersFlow = stickyViewModel.matchingCareers,
            showDatePicker = stickyViewModel.showStickyDatePicker,
            showTypeDropdown = stickyViewModel.showTypeDropdown,
            showInterestDropdown = stickyViewModel.showInterestDropdown,
            onToggleDatePicker = { stickyViewModel.onToggleStickyDatePicker() },
            onShowTypeDropdown = { stickyViewModel.onShowTypeDropdown() },
            onDismissTypeDropdown = { stickyViewModel.onDismissTypeDropdown() },
            onShowInterestDropdown = { stickyViewModel.onShowInterestDropdown() },
            onDismissInterestDropdown = { stickyViewModel.onDismissInterestDropdown() },
            onValueChange = { stickyViewModel.updateSticky(it) },
            onCareerSearchValueChange = { stickyViewModel.onCareerSearchTextChange(it) },
            onCareerSearchValueDone = { stickyViewModel.updateMatchingCareers() },
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
    careerSearchTextFlow: StateFlow<String>,
    matchingCareersFlow: StateFlow<List<String>>,
    showDatePicker: Boolean,
    showTypeDropdown: Boolean,
    showInterestDropdown: Boolean,
    onToggleDatePicker: () -> Unit,
    onShowTypeDropdown: () -> Unit,
    onDismissTypeDropdown: () -> Unit,
    onShowInterestDropdown: () -> Unit,
    onDismissInterestDropdown: () -> Unit,
    onValueChange: (StickyDetails) -> Unit,
    onCareerSearchValueChange: (String) -> Unit,
    onCareerSearchValueDone: () -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier.focusable()
) {
    val stickyUIState by stickyFlow.collectAsState(initial = StickyUIState())
    val careerSearchText by careerSearchTextFlow.collectAsState(initial = "")
    val matchingCareers by matchingCareersFlow.collectAsState(initial = Sticky.defaultCareerList)
    val datePickerState = rememberDatePickerState()
    val focusManager= LocalFocusManager.current

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card (
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                // enter name of activity
                OutlinedTextField(
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
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                )

                // expand the dialogue to include a calendar, add animations later
                StickyDateSetting(
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
                    },
                    modifier = Modifier.focusable()
                )

                // show date picker
                if(showDatePicker) {
                    DatePicker(
                        state = datePickerState
                    )
                }

                // setting to select career
                StickyFieldSetting(
                    painterResourceID = R.drawable.baseline_school_24,
                    settingNameResourceID = R.string.sticky_career_field,
                    settingValueFlow = stickyFlow,
                    careerSearchQuery = careerSearchText,
                    dropdownOptions = matchingCareers,
                    onFieldItemClick = {
                        onValueChange(stickyUIState.stickyDetails.copy(
                            field = it
                        ))
                    },
                    onCareerSearchQueryChange = onCareerSearchValueChange,
                    onCareerSearchQueryDone = onCareerSearchValueDone
                )

                // setting to indicate experience type
                StickyDropdownSetting(
                    painterResourceID = R.drawable.baseline_category_24,
                    settingNameResourceID = R.string.sticky_type,
                    settingValue = stickyUIState.stickyDetails.type,
                    settingValueIconID = stickyUIState.stickyDetails.typeIconID,
                    onShowSettingDropdown = onShowTypeDropdown,
                    onDismissSettingDropdown = onDismissTypeDropdown,
                    displaySettingDropdown = showTypeDropdown,
                    dropdownSettingOptions = listOf(
                        Pair("Research", R.drawable.baseline_lightbulb_24),
                        Pair("Benchmark", R.drawable.baseline_trending_up_24),
                        Pair("Experience", R.drawable.baseline_work_24)
                    ),
                    onItemClick = {
                        val newIconID = when(it) {
                            "Research" -> R.drawable.baseline_lightbulb_24
                            "Benchmark" -> R.drawable.baseline_trending_up_24
                            else -> R.drawable.baseline_work_24
                        }

                        onValueChange(stickyUIState.stickyDetails.copy(
                            type = it,
                            typeIconID = newIconID
                        ))
                    }
                )

                // setting for interest level
                StickyDropdownSetting(
                    painterResourceID = R.drawable.baseline_thumb_up_24,
                    settingNameResourceID = R.string.sticky_interest,
                    settingValue = stickyUIState.stickyDetails.interest,
                    settingValueIconID = stickyUIState.stickyDetails.interestIconID,
                    onShowSettingDropdown = onShowInterestDropdown,
                    onDismissSettingDropdown = onDismissInterestDropdown,
                    displaySettingDropdown = showInterestDropdown,
                    dropdownSettingOptions = listOf(
                        Pair("Low", R.drawable.baseline_signal_cellular_alt_1_bar_24),
                        Pair("Medium", R.drawable.baseline_signal_cellular_alt_2_bar_24),
                        Pair("High", R.drawable.baseline_signal_cellular_alt_24)
                    ),
                    onItemClick = {
                        val newIconID = when(it) {
                            "Low" -> R.drawable.baseline_signal_cellular_alt_1_bar_24
                            "Medium" -> R.drawable.baseline_signal_cellular_alt_2_bar_24
                            else -> R.drawable.baseline_signal_cellular_alt_24
                        }

                        onValueChange(stickyUIState.stickyDetails.copy(
                            interest = it,
                            interestIconID = newIconID
                        ))
                    }
                )

                // enter time committed
                OutlinedTextField(
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
                            Text(
                                text = "Dismiss",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        modifier = Modifier.weight(1F)
                    )
                    TextButton(
                        onClick = onSave,
                        content = {
                            Text(
                                text = "Save",
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