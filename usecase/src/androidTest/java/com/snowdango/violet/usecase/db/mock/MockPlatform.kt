package com.snowdango.violet.usecase.db.mock

import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.platform.PlatformType

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
            platform = PlatformType.Spotify,
            mediaId = "hiohaiofaf",
            url = "oiphaiophiopfawhf"
        ),
        Platform(
            songId = 1,
            platform = PlatformType.AppleMusic,
            mediaId = "ophopjopafa",
            url = "nipohiophaofaw"
        ),
        Platform(
            songId = 2,
            platform = PlatformType.Spotify,
            mediaId = "niophoikjolfasf",
            url = "ipkohfioahiofawfasf"
        ),
        Platform(
            songId = 3,
            platform = PlatformType.AppleMusic,
            mediaId = "jniophiopjfda",
            url = "hiophiopahofipawf"
        ),
        Platform(
            songId = 4,
            platform = PlatformType.AppleMusic,
            mediaId = "njiolhjiopjfpoasf",
            url = "noihiohdoasdsa"
        ),
        Platform(
            songId = 4,
            platform = PlatformType.Spotify,
            mediaId = "jpoifhjopiajfoipwafasfsa",
            url = "iopjiopjfoiasjfoiasjfioasjfoasfas"
        ),
        Platform(
            songId = 4,
            platform = PlatformType.Spotify,
            mediaId = "mopjopajopfjawpofwsa",
            url = "mop[jopajopjasopfjsaf"
        ),
        Platform(
            songId = 5,
            platform = PlatformType.Spotify,
            mediaId = "opjopjopajopfjasopfas",
            url = "opjopjopjopsajfopasfopsajfpasof"
        ),
        Platform(
            songId = 6,
            platform = PlatformType.Spotify,
            mediaId = "jolpjopjopsapfsafsa",
            url = "opjopjopasjopfjsaopfjasopfjsaopfjsap"
        ),
        Platform(
            songId = 7,
            platform = PlatformType.AppleMusic,
            mediaId = "joop[jopfjasopjfopasf",
            url = "ljopjopajfopasfasfsaf"
        )
    )
}