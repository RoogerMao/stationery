package com.example.stationery.ui.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stationery.data.StickyDetails

@Composable
fun StickyCard(
    stickyDetails: StickyDetails = StickyDetails()
) {
    Card(

    ) {
        Row (
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(stickyDetails.typeIconID),
                contentDescription = stickyDetails.type
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Column {
                Text(
                    text = stickyDetails.title,
                )
                HorizontalDivider(thickness = 2.dp)
                Text(
                    text = stickyDetails.field,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stickyDetails.timeCommitted
                )
            }
        }
    }
}

@Preview
@Composable
fun StickyCardPreview(

) {
    StickyCard(StickyDetails(title = "sample title"))
}