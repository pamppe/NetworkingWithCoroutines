package com.emill.networkingwithcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.TypedArrayUtils.getText
import com.emill.networkingwithcoroutines.ui.theme.NetworkingWithCoroutinesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUrl = URL("https://users.metropolia.fi/~jarkkov/folderimage.jpg")
        setContent {
            NetworkingWithCoroutinesTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ShowImage(imageUrl)
                }
            }
        }
    }
}

@Composable
fun ShowImage(imageUrl: URL) {
    var bitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }

    LaunchedEffect(imageUrl) {
        bitmap = getBitmap(imageUrl)?.asImageBitmap()
    }

    Column {
        Text(text = imageUrl.toString()) // Display the URL
        bitmap?.let {
            Image(bitmap = it, contentDescription = "Downloaded Image")
        }
    }
}

// Fetches image from the URL and converts it to a bitmap
private suspend fun getBitmap(url: URL): Bitmap? =
    withContext(Dispatchers.IO) {
        return@withContext BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }