package com.snowdango.violet.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import timber.log.Timber

class AlbumPagingModel(db: SongHistoryDatabase) {

    private val getAlbum: GetAlbum = GetAlbum(db)

    fun getAlbumsPagingSource(): AlbumsPagingSource {
        return AlbumsPagingSource(getAlbum)
    }

    class AlbumsPagingSource(private val getAlbum: GetAlbum) : PagingSource<Int, Album>() {
        override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
            val page = params.key ?: 0
            return try {
                val albums = getAlbum.getAlbums(
                    page * params.loadSize.toLong(),
                    params.loadSize.toLong()
                )
                LoadResult.Page(
                    data = albums,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (albums.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                Timber.d(e.fillInStackTrace())
                LoadResult.Error(e)
            }
        }
    }
}