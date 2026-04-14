package com.example.tuneplay

import android.content.Context
import android.media.MediaPlayer
import kotlin.random.Random

object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    var currentSongIndex = 0
    var isPlaying = false

    val favoriteSongs = mutableSetOf<Int>()

    val songs = listOf(
        Song("Perfect Beat", "TunePlay Artist", R.raw.song1, R.drawable.song1_img),
        Song("Night Vibes", "TunePlay Artist", R.raw.song2, R.drawable.song2_img),
        Song("Dream Waves", "TunePlay Artist", R.raw.song3, R.drawable.song3_img)
    )

    fun playSong(context: Context, index: Int) {
        mediaPlayer?.release()
        currentSongIndex = index
        mediaPlayer = MediaPlayer.create(context, songs[index].audioRes)
        mediaPlayer?.start()
        isPlaying = true
    }

    fun togglePlayPause(context: Context) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPlaying = false
            } else {
                it.start()
                isPlaying = true
            }
        } ?: playSong(context, currentSongIndex)
    }

    fun nextSong(context: Context) {
        currentSongIndex = (currentSongIndex + 1) % songs.size
        playSong(context, currentSongIndex)
    }

    fun previousSong(context: Context) {
        currentSongIndex =
            if (currentSongIndex == 0) songs.size - 1 else currentSongIndex - 1
        playSong(context, currentSongIndex)
    }

    fun shuffleSong(context: Context) {
        val randomIndex = Random.nextInt(songs.size)
        playSong(context, randomIndex)
    }

    fun toggleFavorite() {
        if (favoriteSongs.contains(currentSongIndex)) {
            favoriteSongs.remove(currentSongIndex)
        } else {
            favoriteSongs.add(currentSongIndex)
        }
    }

    fun isFavorite(): Boolean {
        return favoriteSongs.contains(currentSongIndex)
    }

    fun getCurrentSong(): Song {
        return songs[currentSongIndex]
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 1
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun setLoop(loop: Boolean) {
        mediaPlayer?.isLooping = loop
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}