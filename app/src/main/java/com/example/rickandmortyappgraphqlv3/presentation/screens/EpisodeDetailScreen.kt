package com.example.rickandmortyappgraphqlv3.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.presentation.navigation.Screen
import com.example.rickandmortyappgraphqlv3.presentation.screens.states.EpisodeState
import com.example.rickandmortyappgraphqlv3.presentation.screens.viewModels.EpisodeViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val detail = viewModel.episode.value
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121010))
    ) {
        when (detail) {
            is EpisodeState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scroll),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = detail.episode.episode.name, style = TextStyle(
                            fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = detail.episode.episode.airDate, style = TextStyle(
                            fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = detail.episode.episode.episode, style = TextStyle(
                            fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    FlowRow(
                        mainAxisSpacing = 6.dp, crossAxisSpacing = 6.dp
                    ) {
                        detail.episode.characters.forEach { character ->
                            EpisodeDetailResidentsItem(resident = character) {
                                navController.navigate(
                                    Screen.CharacterDetailScreen.route + "/${character.id}"
                                )
                            }
                        }
                    }
                }
            }

            is EpisodeState.Error -> {
                Text(text = "This is an Error Message")
            }

            is EpisodeState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun EpisodeDetailResidentsItem(
    resident: CharacterResultsEntity,
    onItemClick: () -> Unit
) {
    val imagePainter = rememberAsyncImagePainter(
        model = resident.image
    )

    Column(modifier = Modifier
        .border(
            width = 1.dp,
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
        .padding(10.dp)
        .clickable { onItemClick() }
        .width(100.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Text(
            text = resident.name,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}