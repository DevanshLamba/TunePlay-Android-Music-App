package com.example.tuneplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class PlayerScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var refreshUI by remember { mutableStateOf(0) }
            var sliderPos by remember { mutableStateOf(0f) }
            var loopMode by remember { mutableStateOf(false) }

            LaunchedEffect(refreshUI) {
                while (true) {
                    sliderPos = MusicManager.getCurrentPosition().toFloat()
                    delay(500)
                }
            }

            val currentSong = MusicManager.getCurrentSong()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF0A0A0A),
                                Color(0xFF121212),
                                Color(0xFF1A1A2E)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Card(
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF181818)
                        ),
                        elevation = CardDefaults.cardElevation(20.dp),
                        modifier = Modifier.size(340.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF00FFAA),
                                            Color(0xFF1E1E1E),
                                            Color(0xFF00C896)
                                        )
                                    ),
                                    shape = RoundedCornerShape(28.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Image(
                                painter = painterResource(currentSong.imageRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(24.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                currentSong.title,
                                color = Color.White,
                                fontSize = 30.sp
                            )

                            Text(
                                currentSong.artist,
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            onClick = {
                                MusicManager.toggleFavorite()
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                if (MusicManager.isFavorite()) "❤️" else "🤍",
                                fontSize = 24.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Slider(
                        value = sliderPos,
                        onValueChange = {
                            sliderPos = it
                            MusicManager.seekTo(it.toInt())
                        },
                        valueRange = 0f..MusicManager.getDuration().toFloat()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = {
                                MusicManager.shuffleSong(this@PlayerScreen)
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {
                            Text("🔀", color = Color.White)
                        }

                        Button(
                            onClick = {
                                MusicManager.previousSong(this@PlayerScreen)
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {
                            Text("⏮", color = Color.White)
                        }

                        Button(
                            onClick = {
                                MusicManager.togglePlayPause(this@PlayerScreen)
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2A2A2A)
                            )
                        ) {
                            Text(
                                if (MusicManager.isPlaying) "⏸" else "▶",
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                MusicManager.nextSong(this@PlayerScreen)
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {
                            Text("⏭", color = Color.White)
                        }

                        Button(
                            onClick = {
                                loopMode = !loopMode
                                MusicManager.setLoop(loopMode)
                                refreshUI++
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {
                            Text(
                                if (loopMode) "Loop On" else "Loop Off",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}