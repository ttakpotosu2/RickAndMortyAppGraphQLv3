package com.example.rickandmortyappgraphqlv3.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity

@Composable
fun CharacterCard(
    details: CharacterResultsEntity,
    onItemClick: () -> Unit
) {

    val imagePainter = rememberAsyncImagePainter(
        model = details.image
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color.White.copy(.1f))
            .clickable { onItemClick() }
            .clip(RoundedCornerShape(12.dp))
            .border(shape = RoundedCornerShape(12.dp), width = 1.dp, color = Color.White)
//            .placeholder(
//                visible = true,
//                shape = RoundedCornerShape(6.dp),
//                color = Color.LightGray,
//                highlight = PlaceholderHighlight.shimmer(
//                    highlightColor = Color.Red
//                )
//            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = details.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EpisodesItem(
    episodeNumber: String,
    onItemClick: () -> Unit
) {
    Box(modifier = Modifier
        .border(
            width = 1.dp,
            color = Color.White,
            shape = RoundedCornerShape(100.dp)
        )
        .padding(10.dp)
        .clickable { onItemClick() }
    ) {
        Text(
            text = episodeNumber,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}