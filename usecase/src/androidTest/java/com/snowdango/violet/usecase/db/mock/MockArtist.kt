package com.snowdango.violet.usecase.db.mock

import com.snowdango.violet.domain.entity.artists.Artist

object MockArtist {

    fun singleData(): Artist = dataList.random()

    fun over100Data(): List<Artist> {
        val mutableList = mutableListOf<Artist>()
        repeat(6) {
            mutableList.addAll(dataList)
        }
        return mutableList.toList()
    }

    val dataList = listOf(
        Artist(name = "稗田寧々"),
        Artist(name = "Tacitly"),
        Artist(name = "Palette Project"),
        Artist(name = "BABY METAL"),
        Artist(name = "BAND-MAID"),
        Artist(name = "GEMS COMPANY"),
        Artist(name = "Kotone"),
        Artist(name = "Unlucky Morpheus"),
        Artist(name = "田淵智也"),
        Artist(name = "堀江翔太"),
        Artist(name = "アサルトリリィ"),
        Artist(name = "えのぐ"),
        Artist(name = "オルタンシア"),
        Artist(name = "鬼頭明里"),
        Artist(name = "神田ジョン"),
        Artist(name = "田中秀和"),
        Artist(name = "水瀬いのり"),
        Artist(name = "大橋彩香"),
        Artist(name = "睦月周平"),
        Artist(name = "La priere")
    )
}