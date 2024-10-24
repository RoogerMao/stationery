package com.example.stationery.ui.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stationery.R
import com.example.stationery.data.StickyDetails
import com.example.stationery.data.StickyUIState

@Composable
fun StickyCard(
    stickyDetails: StickyDetails = StickyDetails(),
    onDeleteSticky: () -> Unit
) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row (
            modifier = Modifier.padding(8.dp)
        ) {
            var referenceHeight by rememberSaveable { mutableStateOf(0) }

            Column (
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(with(LocalDensity.current) { referenceHeight.toDp() })
            ) {
                Icon(
                    painter = painterResource(stickyDetails.typeIconID),
                    contentDescription = stickyDetails.type,
                    tint = MaterialTheme.colorScheme.tertiary
                )

                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete Sticky",
                    tint = Color.Red,
                    modifier = Modifier.clickable {
                        onDeleteSticky()
                    }
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Column (
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    referenceHeight = layoutCoordinates.size.height
                }
            ) {
                Text(
                    text = stickyDetails.title,
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Career Field: ")
                        }

                        append(stickyDetails.field)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Time Committed: ")
                        }

                        append("${stickyDetails.timeCommitted} Hours")
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun stickyUICardPreview() {
    StickyCard(StickyDetails(), {})
}