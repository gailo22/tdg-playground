package com.example.jetpack.keycloakloginapp.keycloak

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class QrCodeScanner(
    appContext: Context
) {

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        )
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val qrCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan(): String? {
        try {
            val result = scanner.startScan().await()
            qrCodeResults.value = result.rawValue
            Timber.d(qrCodeResults.value)
            return result.rawValue
        } catch (e: Exception) {
            Timber.d("scan error: $e")
            return null
        }
    }

    /* alt:
    scanner.startScan()
    .addOnSuccessListener { barcode ->
        // Task completed successfully
    }
    .addOnCanceledListener {
        // Task canceled
    }
    .addOnFailureListener { e ->
        // Task failed with an exception
    }*/

}