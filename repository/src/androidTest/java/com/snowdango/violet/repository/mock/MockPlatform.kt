package com.snowdango.violet.repository.mock

import com.snowdango.violet.domain.entity.platforms.Platform

object MockPlatform {

    fun singleData() = dataList.random()

    fun over100Data(): List<Platform> {
        val mutableList = mutableListOf<Platform>()
        repeat(11) {
            mutableList.addAll(dataList)
        }
        return mutableList.toList()
    }

    val dataList = listOf(
        Platform(
            songId = 1,
            platform = "spotify",
            mediaId = "hiohaiofaf",
            url = "oiphaiophiopfawhf"
        ),
        Platform(
            songId = 1,
            platform = "apple music",
            mediaId = "ophopjopafa",
            url = "nipohiophaofaw"
        ),
        Platform(
            songId = 2,
            platform = "spotify",
            mediaId = "niophoikjolfasf",
            url = "ipkohfioahiofawfasf"
        ),
        Platform(
            songId = 3,
            platform = "amazon music",
            mediaId = "jniophiopjfda",
            url = "hiophiopahofipawf"
        ),
        Platform(
            songId = 4,
            platform = "nphpijopfjafsa",
            mediaId = "njiolhjiopjfpoasf",
            url = "noihiohdoasdsa"
        ),
        Platform(
            songId = 4,
            platform = "jopjopjfopjofpjopjasfsa",
            mediaId = "jpoifhjopiajfoipwafasfsa",
            url = "iopjiopjfoiasjfoiasjfioasjfoasfas"
        ),
        Platform(
            songId = 4,
            platform = "jopjopjopajopfjasopfjsapfsa",
            mediaId = "mopjopajopfjawpofwsa",
            url = "mop[jopajopjasopfjsaf"
        ),
        Platform(
            songId = 5,
            platform = "iohpiopjiopjafjasfsafsa",
            mediaId = "opjopjopajopfjasopfas",
            url = "opjopjopjopsajfopasfopsajfpasof"
        ),
        Platform(
            songId = 6,
            platform = "mkojopjopajopfaspfsa",
            mediaId = "jolpjopjopsapfsafsa",
            url = "opjopjopasjopfjsaopfjasopfjsaopfjsap"
        ),
        Platform(
            songId = 7,
            platform = "miopjpiojaspfjpoasjfas",
            mediaId = "joop[jopfjasopjfopasf",
            url = "ljopjopajfopasfasfsaf"
        )
    )
}