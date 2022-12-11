package com.snowdango.violet.repository.mock

import com.snowdango.violet.domain.entity.albums.Album

object MockAlbum {

    fun singleData(): Album = dataList.random()

    fun over100Data(): List<Album> {
        val mutableList = mutableListOf<Album>()
        repeat(11) {
            mutableList.addAll(dataList)
        }
        return mutableList.toList()
    }


    val dataList = listOf(
        Album(
            title = "Dialogue+インビテーション",
            artistId = 1,
            thumbnailUrl = "sssssssssssssss"
        ),
        Album(
            title = "直感オーバーライド",
            artistId = 2,
            thumbnailUrl = "wssssssssssss"
        ),
        Album(
            title = "存在アピ",
            artistId = 2,
            thumbnailUrl = "sfawhuifawe"
        ),
        Album(
            title = "VOICE",
            artistId = 2,
            thumbnailUrl = "safqfaawsfw"
        ),
        Album(
            title = "Dearマイフレンド",
            artistId = 13,
            thumbnailUrl = "njioawhfuioaehf"
        ),
        Album(
            title = "君とインフィニティ",
            artistId = 13,
            thumbnailUrl = "fafnioafjawiofopawj"
        ),
        Album(
            title = "今回の騒動につきまして",
            artistId = 7,
            thumbnailUrl = "jnopjopjdopawd"
        ),
        Album(
            title = "Autonomy",
            artistId = 7,
            thumbnailUrl = "ji0fhaiohfawe"
        ),
        Album(
            title = "NOMATCHING",
            artistId = 7,
            thumbnailUrl = "nihioafawfaw"
        ),
        Album(
            title = "アンダーワールドウタウタイ",
            artistId = 7,
            thumbnailUrl = "niofhaiofwoa"
        )
    )

}