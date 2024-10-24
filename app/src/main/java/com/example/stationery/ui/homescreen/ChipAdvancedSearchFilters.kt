package com.example.stationery.ui.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stationery.R
import com.example.stationery.logic.model.SearchViewModel

@Composable
fun AdvancedSearchChipRow(
    searchViewModel: SearchViewModel
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            AdvancedSearchChip(
                index = 0,
                searchViewModel = searchViewModel,
                toggleSelection = { searchViewModel.toggleSelectedChip(0) },
                searchLabel = stringResource(R.string.sticky_date),
                leadingIconID = R.drawable.baseline_calendar_today_24
            )
        }

        item {
            AdvancedSearchChip(
                index = 1,
                searchViewModel = searchViewModel,
                toggleSelection = { searchViewModel.toggleSelectedChip(1) },
                searchLabel = stringResource(R.string.sticky_career_field),
                leadingIconID = R.drawable.baseline_school_24
            )
        }

        item {
            AdvancedSearchChip(
                index = 2,
                searchViewModel = searchViewModel,
                toggleSelection = { searchViewModel.toggleSelectedChip(2) },
                searchLabel = stringResource(R.string.sticky_type),
                leadingIconID = R.drawable.baseline_category_24
            )
        }

        item {
            AdvancedSearchChip(
                index = 3,
                searchViewModel = searchViewModel,
                toggleSelection = { searchViewModel.toggleSelectedChip(3) },
                searchLabel = "Time",
                leadingIconID = R.drawable.baseline_schedule_24
            )
        }

        item {
            AdvancedSearchChip(
                index = 4,
                searchViewModel = searchViewModel,
                toggleSelection = { searchViewModel.toggleSelectedChip(4) },
                searchLabel = stringResource(R.string.sticky_interest),
                leadingIconID = R.drawable.baseline_thumb_up_24
            )
        }
    }
}

@Composable
fun AdvancedSearchChip(
    index: Int,
    searchViewModel: SearchViewModel,
    toggleSelection: (Int) -> Unit,
    searchLabel: String,
    leadingIconID: Int
) {
    Log.d("debugging", "Recomposed!")
    val selected = searchViewModel.chipsSelected[index]
    val enabled = searchViewModel.chipsEnabled[index]

    FilterChip(
        onClick = { toggleSelection(index) },
        label = { Text(text = searchLabel) },
        selected = selected,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    painter = painterResource(leadingIconID),
                    contentDescription = "Advanced searching using $searchLabel"
                )
            }
        } else null
    )
}