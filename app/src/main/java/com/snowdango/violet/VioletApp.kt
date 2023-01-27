package com.snowdango.violet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.color.DynamicColors
import com.snowdango.violet.domain.memory.InMemoryStore
import com.snowdango.violet.model.data.TwitterTokenModel
import com.snowdango.violet.model.paging.SongHistoryModel
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.api.provide.ApiProvider
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.repository.datastore.TwitterTokenDataStore
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.connect.ConnectManager
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import timber.log.Timber

class VioletApp : Application() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "last_song")

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        DynamicColors.applyToActivitiesIfAvailable(this)
        startKoin {
            androidContext(applicationContext)
            modules(
                module {
                    single { LastSongDataStore(applicationContext.dataStore) }
                    single { SongHistoryDatabase.getInstance(applicationContext) }
                    single { InMemoryStore() }
                    single { TwitterTokenDataStore(applicationContext.dataStore) }
                    factory { ApiRepository(ApiProvider()) }
                    factory { ConnectManager(get()) }
                    factory { TwitterTokenModel() }
                },
                module {// history screen
                    factory { SongHistoryModel(get()) }
                    viewModel { HistoryViewModel() }
                }
            )
        }
    }

}