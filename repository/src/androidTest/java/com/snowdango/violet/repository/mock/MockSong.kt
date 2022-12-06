package com.snowdango.violet.repository.mock

import com.snowdango.violet.domain.entity.songs.Song

object MockSong {

    fun singleData() = dataList.random()

    fun over100Data(): List<Song> {
        val mutableList = mutableListOf<Song>()
        repeat(11) {
            mutableList.addAll(dataList)
        }
        return mutableList.toList()
    }

    val dataList = listOf(
        Song(
            title = "さよならからはじまり",
            artistId = 1,
            albumId = 1,
            thumbnailUrl = "nkoawfhaoi"
        ),
        Song(
            title = "ひとりぼっち東京",
            artistId = 2,
            albumId = 2,
            thumbnailUrl = "ioahjfiohawiof"
        ),
        Song(
            title = "ギターと孤独と蒼い惑星",
            artistId = 2,
            albumId = 2,
            thumbnailUrl = "njiopfhoipawhjfo"
        ),
        Song(
            title = "夏雪ときめきハイテンション！",
            artistId = 2,
            albumId = 4,
            thumbnailUrl = "ioahjiofhiowaf"
        ),
        Song(
            title = "Best Blood Blend",
            artistId = 1,
            albumId = 5,
            thumbnailUrl = "nioahfoihawof"
        ),
        Song(
            title = "life hacks",
            artistId = 3,
            albumId = 1,
            thumbnailUrl = "jopfjapf"
        ),
        Song(
            title = "会いたいボクラ",
            artistId = 4,
            albumId = 4,
            thumbnailUrl = "oihoifhafda"
        ),
        Song(
            title = "灼熱にて純情(wii-wii-woo)",
            artistId = 3,
            albumId = 6,
            thumbnailUrl = "nmophaiopfhaow"
        ),
        Song(
            title = "こんみゅるんと☆スタート",
            artistId = 2,
            albumId = 7,
            thumbnailUrl = "njopajpfojawpf"
        ),
        Song(
            title = "アカペラハート",
            artistId = 7,
            albumId = 3,
            thumbnailUrl = "opjpofjawopf"
        )
    )
}