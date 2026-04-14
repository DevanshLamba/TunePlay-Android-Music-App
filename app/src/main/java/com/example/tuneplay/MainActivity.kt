package com.example.tuneplay

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var refreshUI by remember { mutableStateOf(0) }

            val currentSong = MusicManager.getCurrentSong()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(16.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "TunePlay",
                        color = Color.White,
                        fontSize = 28.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(MusicManager.songs) { index, song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    MusicManager.playSong(this@MainActivity, index)
                                    refreshUI++
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            PlayerScreen::class.java
                                        )
                                    )
                                }
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(song.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(song.title, color = Color.White, fontSize = 20.sp)
                                Text(song.artist, color = Color.Gray)
                            }
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E1E)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(currentSong.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(currentSong.title, color = Color.White)
                                Text(
                                    currentSong.artist,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Row {
                            Button(onClick = {
                                MusicManager.previousSong(this@MainActivity)
                                refreshUI++
                            }) {
                                Text("⏮")
                            }

                            Button(onClick = {
                                MusicManager.togglePlayPause(this@MainActivity)
                                refreshUI++
                            }) {
                                Text(if (MusicManager.isPlaying) "⏸" else "▶")
                            }

                            Button(onClick = {
                                MusicManager.nextSong(this@MainActivity)
                                refreshUI++
                            }) {
                                Text("⏭")
                            }
                        }
                    }
                }
            }
        }
    }
}