package com.snowdango.violet.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingDataStore(private val dataStore: DataStore<Preferences>) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun putString(key: String, value: String?) {
        value?.let { string ->
            coroutineScope.launch {
                dataStore.edit { it[stringPreferencesKey(key)] = string }
            }
        }
    }

    fun putStringSet(key: String, values: MutableSet<String>?) {
        values?.let { stringSet ->
            coroutineScope.launch {
                dataStore.edit { it[stringSetPreferencesKey(key)] = stringSet.toSet() }
            }
        }
    }

    fun putInt(key: String, value: Int) {
        coroutineScope.launch {
            dataStore.edit { it[intPreferencesKey(key)] = value }
        }
    }

    fun putLong(key: String, value: Long) {
        coroutineScope.launch {
            dataStore.edit { it[longPreferencesKey(key)] = value }
        }
    }


    fun putFloat(key: String, value: Float) {
        coroutineScope.launch {
            dataStore.edit { it[floatPreferencesKey(key)] = value }
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        coroutineScope.launch {
            dataStore.edit { it[booleanPreferencesKey(key)] = value }
        }
    }

    fun getString(key: String, defValue: String?): String? {
        return runBlocking {
            dataStore.data.map { it[stringPreferencesKey(key)] ?: defValue!! }.firstOrNull()
        }
    }

    fun getStringSet(key: String, defValues: MutableSet<String>?): Set<String>? {
        return runBlocking {
            dataStore.data.map { it[stringSetPreferencesKey(key)] ?: defValues }.firstOrNull()
        }
    }

    fun getInt(key: String, defValue: Int): Int {
        return runBlocking {
            dataStore.data.map { it[intPreferencesKey(key)] ?: defValue }.first()
        }
    }

    fun getLong(key: String, defValue: Long): Long {
        return runBlocking {
            dataStore.data.map { it[longPreferencesKey(key)] ?: defValue }.first()
        }
    }

    fun getFloat(key: String, defValue: Float): Float {
        return runBlocking {
            dataStore.data.map { it[floatPreferencesKey(key)] ?: defValue }.first()
        }
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return runBlocking {
            dataStore.data.map { it[booleanPreferencesKey(key)] ?: defValue }.first()
        }
    }

    fun getStringFlow(key: String, defValue: String): Flow<String> {
        return dataStore.data.map { it[stringPreferencesKey(key)] ?: defValue }
    }

    fun getStringSetFlow(key: String, defValue: Set<String>): Flow<Set<String>> {
        return dataStore.data.map { it[stringSetPreferencesKey(key)] ?: defValue }
    }

    fun getIntFlow(key: String, defValue: Int): Flow<Int> {
        return dataStore.data.map { it[intPreferencesKey(key)] ?: defValue }
    }

    fun getLongFlow(key: String, defValue: Long): Flow<Long> {
        return dataStore.data.map { it[longPreferencesKey(key)] ?: defValue }
    }

    fun getFloatFlow(key: String, defValue: Float): Flow<Float> {
        return dataStore.data.map { it[floatPreferencesKey(key)] ?: defValue }
    }

    fun getBooleanFlow(key: String, defValue: Boolean): Flow<Boolean> {
        return dataStore.data.map { it[booleanPreferencesKey(key)] ?: defValue }
    }

}