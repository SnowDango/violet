package com.snowdango.violet.domain.memory

import com.snowdango.violet.domain.last.LastSong
import kotlinx.coroutines.sync.Mutex

class InMemoryStore {

    var lastSong: LastSong? = null
    
    val mutex = Mutex()

}