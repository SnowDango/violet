package com.snowdango.violet.model

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.violet.domain.relation.HistoryWithSong
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.history.GetHistory

class SongHistoryModel(context: Context) {

    private val getHistory: GetHistory = GetHistory(SongHistoryDatabase.getInstance(context))

    fun getSongHistoriesPagingSource(): SongHistoryPagingSource {
        return SongHistoryPagingSource(getHistory)
    }

    class SongHistoryPagingSource(private val getHistory: GetHistory) :
        PagingSource<Int, HistoryWithSong>() {

        override fun getRefreshKey(state: PagingState<Int, HistoryWithSong>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryWithSong> {
            val page = params.key ?: 0
            return try {
                val histories = getHistory.getHistoriesWithSong(
                    page * params.loadSize.toLong(),
                    params.loadSize.toLong()
                )
                LoadResult.Page(
                    data = histories,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (histories.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}