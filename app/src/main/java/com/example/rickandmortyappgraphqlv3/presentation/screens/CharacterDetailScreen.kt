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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyappgraphqlv3.R
import com.example.rickandmortyappgraphqlv3.presentation.EpisodesItem
import com.example.rickandmortyappgraphqlv3.presentation.navigation.Screen
import com.example.rickandmortyappgraphqlv3.presentation.screens.states.CharacterState
import com.example.rickandmortyappgraphqlv3.presentation.screens.viewModels.CharacterDetailViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val detail = viewModel.character.value
    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121010))
    ) {
        when (detail) {
            is CharacterState.Success -> {
                val imagePainter = rememberAsyncImagePainter(
                    model = detail.character.character.image
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scroll),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    IconButton(
                        onClick = { navHostController.navigateUp() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = detail.character.character.name, style = TextStyle(
                            fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Text(
                        text = detail.character.character.status,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (detail.character.character.status == "Alive") Color.Green else Color.Red
                            )
                            .padding(horizontal = 16.dp),
                        style = TextStyle(
                            fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "Gender: ${detail.character.character.gender}", style = TextStyle(
                            fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "Species: ${detail.character.character.species}", style = TextStyle(
                            fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Normal
                        )
                    )
//                    Text(
//                        text = "Origin: ${detail.character.origin.name}",
//                        style = TextStyle(
//                            fontSize = 20.sp,
//                            color = Color.White,
//                            fontWeight = FontWeight.Normal
//                        )
//                    )
                    Text(
                        text = stringResource(id = R.string.character_detail_page_text_one),
                        style = TextStyle(
                            fontSize = 20.sp, color = Color.White
                        )
                    )
                    FlowRow(
                        mainAxisSpacing = 10.dp, crossAxisSpacing = 10.dp
                    ) {
                        detail.character.episodes.forEach { episode ->
                            EpisodesItem(episodeNumber = episode.episode, onItemClick = {
                                navHostController.navigate(
                                    Screen.EpisodeDetailScreen.route + "/${episode}"
                                )
                            })
                        }
                    }
                }
            }

            is CharacterState.Error -> {
                Text(text = "This is an Error Message")
            }

            is CharacterState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp), color = Color.White
                    )
                }
            }
        }
    }
}