package com.example.stationery.ui.homescreen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.stationery.R
import com.example.stationery.data.StickyUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StickyDateSetting(
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

@Composable
fun StickyDropdownSetting(
    painterResourceID: Int,
    onShowSettingDropdown: () -> Unit,
    onDismissSettingDropdown: () -> Unit,
    displaySettingDropdown: Boolean,
    dropdownSettingOptions: List<Pair<String, Int>>,
    settingNameResourceID: Int,
    settingValue: String,
    settingValueIconID: Int,
    onItemClick: (String) -> Unit,                 // what to display
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
        Icon(
            painter = painterResource(settingValueIconID),
            contentDescription = settingValue
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        StickySettingDropdownMenu(
            text = settingValue,
            onShowDropdown = onShowSettingDropdown,
            onDismissDropdown = onDismissSettingDropdown,
            displayDropdown = displaySettingDropdown,
            dropdownOptions = dropdownSettingOptions,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun StickySettingDropdownMenu(
    text: String,
    onShowDropdown: () -> Unit,
    onDismissDropdown: () -> Unit,
    displayDropdown: Boolean = false,
    dropdownOptions: List<Pair<String, Int>>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    // for a nice ripple when we click
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Text(
        text = text,
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        onShowDropdown()
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())

                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
            .indication(interactionSource, LocalIndication.current)
    )

    DropdownMenu(
        expanded = displayDropdown,
        onDismissRequest = onDismissDropdown,
        offset = pressOffset.copy(
            y = pressOffset.y - itemHeight
        )
    ) {
        dropdownOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option.first) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(option.second),
                        contentDescription = option.first
                    )
                },
                onClick = {
                    onItemClick(option.first)
                    onDismissDropdown()
                }
            )
        }
    }
}

@Composable
fun StickyFieldSetting(
    painterResourceID: Int,
    settingNameResourceID: Int,
    settingValueFlow: StateFlow<StickyUIState>,
    careerSearchQuery: String,
    dropdownOptions: List<String>,
    onFieldItemClick: (String) -> Unit,
    onCareerSearchQueryChange: (String) -> Unit,
    onCareerSearchQueryDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val settingName = stringResource(id = settingNameResourceID)
    val stickyUIState by settingValueFlow.collectAsState()
    val settingValue = stickyUIState.stickyDetails.field

    Column {
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        StickyCareerSearchBar(
            settingValueFlow = settingValueFlow,
            careerSearchQuery = careerSearchQuery,
            dropdownOptions = dropdownOptions,
            onFieldItemClick = onFieldItemClick,
            onCareerSearchQueryChange = onCareerSearchQueryChange,
            onCareerSearchQueryDone = onCareerSearchQueryDone,
            modifier = Modifier.focusable()
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }

}

@Composable
fun StickyCareerSearchBar(
    settingValueFlow: StateFlow<StickyUIState>,
    careerSearchQuery: String,
    dropdownOptions: List<String>,
    onFieldItemClick: (String) -> Unit,
    onCareerSearchQueryChange: (String) -> Unit,
    onCareerSearchQueryDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expandedMenu by remember { mutableStateOf(false) }
    val stickyUIState by settingValueFlow.collectAsState()

    TextField(
        value = careerSearchQuery,
        onValueChange = {
            onCareerSearchQueryChange(it)
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier.clickable {
                    onCareerSearchQueryDone()
                    expandedMenu = true
                }
            )
        },
        placeholder = {
            Text(text = "Search Career List Here")
        }
    )

    DropdownMenu(
        expanded = expandedMenu,
        onDismissRequest = { expandedMenu = false },
        modifier = Modifier.sizeIn(
            maxHeight = 250.dp
        )
    ) {
        dropdownOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option) },
                onClick = {
                    onFieldItemClick(option)
                    expandedMenu = false
                }
            )
        }
    }
}