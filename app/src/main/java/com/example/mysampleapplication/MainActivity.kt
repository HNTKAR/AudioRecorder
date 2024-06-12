package com.example.mysampleapplication

import android.content.pm.PackageManager
import android.graphics.RecordingCanvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.mysampleapplication.ui.theme.MySampleApplicationTheme

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MySampleApplicationTheme {
                RequestAudioPermissions()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MySampleApplicationTheme {
        RequestAudioPermissions()
    }
}

@Composable
fun RequestAudioPermissions() {
    var permissionGranted by remember  {mutableStateOf(false)}

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    val lca=LocalContext.current as Activity
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                lca,
                Manifest.permission.RECORD_AUDIO
            ) -> {
                permissionGranted = true
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
        Log.d("msg",permissionGranted.toString())
    }

    Scaffold {padding->
        if (permissionGranted) {
            Text(text = "録音の権限が許可されました", modifier = Modifier.padding(padding).background(
                Color.Blue
            ))
        } else {
            Text(text = "録音の権限が必要です" ,modifier = Modifier.padding(padding).background(
                Color.Red
            ))
//            Text(text = Padding.toString())
        }
    }

//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        if (permissionGranted) {
//            Text(text = "録音の権限が許可されました")
//        } else {
//            Text(text = "録音の権限が必要です")
//        }
//    }
}