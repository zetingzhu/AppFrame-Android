package com.zzt.zt_android_datalayer

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zzt.act.NewAct
import com.zzt.act.NewProcessAct
import com.zzt.zt_android_datalayer.ui.theme.AppFrameAndroidTheme
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    val TAG = MainActivity::class.java.simpleName

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "延迟执行 0 " + Thread.currentThread().name)
        setContent {
            AppFrameAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    val scope = rememberCoroutineScope()
                    val context = LocalContext.current
                    scope.launch {
                        delay(6000)
                        Log.d(TAG, "延迟执行 1 " + Thread.currentThread().name)

                        NewAct.start(context)
                    }
                }
            }
        }

        // 在后台线程执行一些耗时操作
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000) // 模拟耗时操作
            Log.d(TAG, "延迟执行 2 " + Thread.currentThread().name)
            withContext(Dispatchers.Main.immediate) {

                Log.d(TAG, "延迟执行 3 " + Thread.currentThread().name)
            }
        }

        runBlocking {
            Log.d(TAG, "延迟执行 4 " + Thread.currentThread().name)
            launch(Dispatchers.IO + CoroutineName("MyIOCoroutine")) {
                Log.d(TAG, "延迟执行 5 " + Thread.currentThread().name)
                delay(1000)
                Log.d(TAG, "延迟执行 6 " + Thread.currentThread().name)
            }
            Log.d(TAG, "延迟执行 7 " + Thread.currentThread().name)
        }
        Log.d(
            TAG,
            "延迟执行 10 " + Thread.currentThread().name + " <<${android.os.Process.myPid()}>>"
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
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