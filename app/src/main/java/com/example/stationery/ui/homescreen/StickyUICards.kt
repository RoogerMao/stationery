package com.example.stationery.ui.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stationery.data.StickyDetails

@Composable
fun StickyCard(
    stickyDetails: StickyDetails = StickyDetails()
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
            Icon(
                painter = painterResource(stickyDetails.typeIconID),
                contentDescription = stickyDetails.type,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Column {
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