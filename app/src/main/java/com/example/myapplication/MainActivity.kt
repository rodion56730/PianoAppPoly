package com.example.myapplication


import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.window.layout.WindowMetricsCalculator



class MainActivity : ComponentActivity() {
    private var permissionsGranted = false
    private lateinit var micManager: MicrophoneManager

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
            CheckPermissions()
            micManager = MicrophoneManager(LocalContext.current)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckPermissions() {
        val context = LocalContext.current
        val permissions = arrayOf(permission.READ_MEDIA_AUDIO, permission.RECORD_AUDIO)
        val requestPermission =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            { permissionsMap ->
                val isGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (isGranted) {
                    Toast.makeText(context, "Разрешения получены", Toast.LENGTH_LONG).show()
                }
            }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                permission.RECORD_AUDIO
            ) -> {
                permissionsGranted = true
            }

            else -> {
                SideEffect {
                    requestPermission.launch(permissions)
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun MainContent() {
        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)
        val widthDp = metrics.bounds.width() /
                resources.displayMetrics.density
        val heightDp = metrics.bounds.height() /
                resources.displayMetrics.density

        val whKeySizeWidth = ((widthDp - 56) / 14).dp
        val whKeySizeHeight = (heightDp / 3 * 2).dp
        Column {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    horizontalArrangement = Arrangement.Start
                ) {

                    Button(onClick = {
                        micManager.startRecording()
                    },modifier = Modifier
                        .size(50.dp)
                        .padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ){}
                    Button(
                        onClick = { micManager.stopRecording() },
                        colors = ButtonDefaults.buttonColors(Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(8.dp)
                            .size(35.dp)
                    ) {
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    horizontalArrangement = Arrangement.Start
                ) {
                    WhiteKeyItem(item = KeyModel(1, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(3, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(5, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(6, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(8, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(10, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(12, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(13, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(15, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(17, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(18, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(20, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(22, whKeySizeHeight, whKeySizeWidth))
                    WhiteKeyItem(item = KeyModel(24, whKeySizeHeight, whKeySizeWidth))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    val coef = when {
                        heightDp < 400f -> 1.5f
                        else -> 1.4f
                    }
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * 2f))
                    BlackKeyItem(item = KeyModel(2, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(4, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth * 0.8f))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * 2f))
                    BlackKeyItem(item = KeyModel(7, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(9, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(11, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth * 0.8f))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * 2f))
                    BlackKeyItem(item = KeyModel(14, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(16, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth * 0.8f))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * 2f))
                    BlackKeyItem(item = KeyModel(19, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(21, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth / 3 * coef))
                    BlackKeyItem(item = KeyModel(23, whKeySizeHeight, whKeySizeWidth))
                    Spacer(modifier = Modifier.width(whKeySizeWidth * 0.8f))
                }
            }
        }
    }
}

