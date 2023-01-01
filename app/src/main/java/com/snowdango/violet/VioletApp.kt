package com.snowdango.violet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.api.provide.ApiProvider
import com.snowdango.violet.repository.db.SongHistoryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import timber.log.Timber

class VioletApp : Application() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "last_song")

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(applicationContext)
            modules(
                module {
                    single { applicationContext.dataStore }
                    single { SongHistoryDatabase.getInstance(applicationContext) }
                    factory { ApiRepository(ApiProvider()) }
                }
            )
        }
    }

}