package com.snowdango.db.mock

import com.snowdango.db.domain.entity.histories.History
import kotlinx.datetime.LocalDateTime

object MockHistory {

    fun singleData() = dataList.random()

    fun over100Data(): List<History> {
        val mutableList = mutableListOf<History>()
        repeat(11) {
            mutableList.addAll(dataList)
        }
        return mutableList.toList()
    }

    val dataList = listOf(
        History(
            songId = 3,
            dateTime = LocalDateTime.parse("2021-11-16T12:34")
        ),
        History(
            songId = 1,
            dateTime = LocalDateTime.parse("2021-12-16T12:34")
        ),
        History(
            songId = 4,
            dateTime = LocalDateTime.parse("2021-11-17T12:34")
        ),
        History(
            songId = 3,
            dateTime = LocalDateTime.parse("2021-11-16T12:33")
        ),
        History(
            songId = 2,
            dateTime = LocalDateTime.parse("2021-11-16T11:34")
        ),
        History(
            songId = 6,
            dateTime = LocalDateTime.parse("2021-10-16T12:34")
        ),
        History(
            songId = 5,
            dateTime = LocalDateTime.parse("2021-11-16T10:24")
        ),
        History(
            songId = 6,
            dateTime = LocalDateTime.parse("2021-11-16T12:38")
        ),
        History(
            songId = 3,
            dateTime = LocalDateTime.parse("2021-11-16T12:14")
        ),
        History(
            songId = 3,
            dateTime = LocalDateTime.parse("2021-11-16T12:34")
        )
    )

}