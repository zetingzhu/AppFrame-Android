package com.zzt.zt_android_datalayer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zzt.act.NewAct
import com.zzt.act.NewProcessAct
import com.zzt.zt_android_datalayer.ui.theme.AppFrameAndroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFrameAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

                Log.d(TAG, "生命周期监听：Lifecycle.State.CREATED ")
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                Log.d(TAG, "生命周期监听：Lifecycle.State.STARTED ")
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                Log.d(TAG, "生命周期监听：Lifecycle.State.RESUMED ")
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = {}) {
            Text(text = "官方 dataLayer 中 dataStore ")
        }

        Button(onClick = {
            NewAct.start(context)
        }) {
            Text(text = "当前进程 打卡")
        }

        Button(onClick = {
            NewProcessAct.start(context)
        }) {
            Text(text = "新进程 打开")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFrameAndroidTheme {
        Greeting("Android")
    }
}