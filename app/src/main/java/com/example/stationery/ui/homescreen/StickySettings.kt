package com.example.stationery.ui.homescreen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import kotlin.math.exp

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
        modifier = Modifier.onSizeChanged {
            itemHeight = with(density) { it.height.toDp() }
        }.pointerInput(true) {
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
        }.indication(interactionSource, LocalIndication.current)
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
    settingValue: String,
    careerSearchQuery: String,
    showFieldDropdown: Boolean,
    dropdownOptions: List<String>,
    onDismissFieldDropdown: () -> Unit,
    onFieldItemClick: (String) -> Unit,
    onCareerSearchQueryChange: (String) -> Unit,
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
        StickyCareerSearchBar(
            settingValue = settingValue,
            careerSearchQuery = careerSearchQuery,
            showFieldDropdown = showFieldDropdown,
            dropdownOptions = dropdownOptions,
            onDismissFieldDropdown = onDismissFieldDropdown,
            onFieldItemClick = onFieldItemClick,
            onCareerSearchQueryChange = onCareerSearchQueryChange
        )
    }
}

@Composable
fun StickyCareerSearchBar(
    settingValue: String,
    careerSearchQuery: String,
    showFieldDropdown: Boolean,
    dropdownOptions: List<String>,
    onDismissFieldDropdown: () -> Unit,
    onFieldItemClick: (String) -> Unit,
    onCareerSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = careerSearchQuery,
        onValueChange = onCareerSearchQueryChange,
        placeholder = {
            Text(text = settingValue, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    )

    DropdownMenu(
        expanded = showFieldDropdown,
        onDismissRequest = onDismissFieldDropdown,
        modifier = Modifier.height((5 * 60).dp) // place holder constant
    ) {
        dropdownOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option) },
                onClick = {
                    onFieldItemClick(option)
                    onDismissFieldDropdown()
                }
            )
        }
    }
}