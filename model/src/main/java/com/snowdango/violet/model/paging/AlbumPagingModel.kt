package com.snowdango.violet.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.violet.domain.relation.AlbumWithArtist
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import timber.log.Timber

class AlbumWithArtistPagingModel(db: SongHistoryDatabase) {

    private val getAlbum: GetAlbum = GetAlbum(db)

    fun getAlbumWithArtistPagingSource(): AlbumWithArtistPagingSource {
        return AlbumWithArtistPagingSource(getAlbum)
    }

    class AlbumWithArtistPagingSource(private val getAlbum: GetAlbum): PagingSource<Int, AlbumWithArtist>() {
        override fun getRefreshKey(state: PagingState<Int, AlbumWithArtist>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumWithArtist> {
            val page = params.key ?: 0
            return try {
                val albums = getAlbum.getAlbumsWithArtist(
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