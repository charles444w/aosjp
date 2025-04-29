package jcp.apps
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

@Composable
fun DocumentScanner() {
    val activity = LocalContext.current as Activity
    // 配置掃描選項
    val options = remember {
        GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(true)
            .setPageLimit(1)
            .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG)
            .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
            .build()
    }

    // 獲取掃描器客戶端
    val scanner = remember { GmsDocumentScanning.getClient(options) }

    // 儲存掃描圖片的 URI
    var scannedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 啟動掃描結果的處理
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val scanResult = GmsDocumentScanningResult.fromActivityResultIntent(intent)
                // 嘗試獲取圖片 URI
                scannedImageUri = scanResult?.pages?.firstOrNull()?.imageUri
                    ?: intent.getParcelableArrayListExtra<Uri>("com.google.mlkit.vision.documentscanner.RESULT_IMAGES")?.firstOrNull()
            }
        }
    }

    // 按鈕啟動掃描
    Button(onClick = {

        scanner.getStartScanIntent(activity)
            .addOnSuccessListener { intentSender ->
                launcher.launch(IntentSenderRequest.Builder(intentSender).build())
            }
            .addOnFailureListener { /* 處理失敗（例如記錄錯誤） */ }
    }) {
        Text("掃描文件")
    }

    // 如果有掃描圖片，則顯示
    scannedImageUri?.let { uri ->
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "掃描的文件",
            modifier = Modifier.fillMaxWidth()
        )
    }
}