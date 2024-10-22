package com.example.stationery.ui.homescreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stationery.data.StickyDetails

@Composable
fun StickyCard(
    stickyDetails: StickyDetails = StickyDetails()
) {
    Card(

    ) {
        Text(
            text = stickyDetails.title,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun StickyCardPreview(

) {
    StickyCard()
}