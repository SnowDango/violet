package com.snowdango.violet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.color.DynamicColors
import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.model.data.DeleteHistoryModel
import com.snowdango.violet.model.data.GetAlbumAllMetaModel
import com.snowdango.violet.model.data.GetSongAllMetaModel
import com.snowdango.violet.model.paging.AlbumPagingModel
import com.snowdango.violet.model.paging.SongHistoryPagingModel
import com.snowdango.violet.presenter.fragment.setting.SettingFragment
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.api.provide.ApiProvider
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.connect.ConnectManager
import com.snowdango.violet.viewmodel.album.AlbumViewModel
import com.snowdango.violet.viewmodel.album_detail.AlbumDetailViewModel
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import timber.log.Timber

class VioletApp : Application() {

    private val Context.lastSongDataStore: DataStore<Preferences> by preferencesDataStore(name = "last_song")
    // private val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        DynamicColors.applyToActivitiesIfAvailable(this)
        startKoin {
            androidContext(applicationContext)
            modules(
                module {
                    single { LastSongDataStore(applicationContext.lastSongDataStore) }
                    single { SongHistoryDatabase.getInstance(applicationContext) }
                    single { InMemoryStore() }
                    factory { ApiRepository(ApiProvider()) }
                    factory { ConnectManager(get()) }
                    scope<SettingFragment> {
                    }
                },
                module {// history
                    factory { SongHistoryPagingModel(get()) }
                    factory { GetSongAllMetaModel(get()) }
                    factory { DeleteHistoryModel(get()) }
                    viewModel { HistoryViewModel() }
                },
                module {// album
                    factory { AlbumPagingModel(get()) }
                    viewModel { AlbumViewModel() }
                },
                module {
                    factory { GetAlbumAllMetaModel(get()) }
                    viewModel { AlbumDetailViewModel() }
                }
            )
        }
    }

}