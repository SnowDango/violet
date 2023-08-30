package com.snowdango.violet.repository.file

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.storage.StorageManager
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class ArtworkFileManager(private val context: Context) : KoinComponent {

    suspend fun saveAlbumFile(base64: String?): String? = withContext(Dispatchers.IO) {
        Timber.d("saveAlbumFile")
        if (base64.isNullOrBlank()) return@withContext null

        val artwork: Bitmap = base64ToBitmap(base64)
        val imageUuid = UUID.randomUUID().toString()

        requestStorage(artwork.allocationByteCount.toLong(), imageUuid)?.let { file ->
            isSuccessImageSave(bitmapToByteArray(artwork), file)
            return@withContext file.path
        }

        return@withContext null
    }

    private fun requestStorage(imageSize: Long, imageUuid: String): File? { // is storage enough?
        val file = File(context.filesDir, "$imageUuid.jpeg")
        val storageManager: StorageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val storageUuid = storageManager.getUuidForPath(file)
        val freeStorage = storageManager.getAllocatableBytes(storageUuid)
        return if (imageSize < freeStorage) {
            file
        } else {
            null
        }
    }

    private fun isSuccessImageSave(image: ByteArray, file: File) { // save image and it result
        try {
            val fileOutputStream = file.outputStream()
            fileOutputStream.write(image)
            fileOutputStream.close()
        } catch (e: Exception) {
            Timber.tag("ArtworkFileManager").e("image save error")
        }
    }

    private fun base64ToBitmap(base64: String): Bitmap {
        val decodedString = Base64.decode(base64.substring(base64.indexOf(",") + 1), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}