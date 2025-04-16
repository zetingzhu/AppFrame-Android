package com.zzt.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zzt.datastore.DataStoreUtil
import com.zzt.datastore.MultiProcessDataStoreUtil
import com.zzt.util.SystemUtil
import com.zzt.zt_android_datalayer.R

class NewProcessAct : AppCompatActivity() {
    val TAG = NewProcessAct::class.java.simpleName

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, NewProcessAct::class.java)
            context.startActivity(starter)
        }
    }

    var textView: TextView? = null
    var textView_ds: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_process)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val processName = SystemUtil.getProcessName(this)
        Log.d(TAG, "新进程名字：" + processName)

        initView()

        getDataCount()

        addDataStore()
    }

    private fun initView() {
        textView = findViewById(R.id.textView)
        textView_ds = findViewById(R.id.textView_ds)

        findViewById<Button>(R.id.btn_new).setOnClickListener {
            NewAct.start(this@NewProcessAct)
        }

        findViewById<Button>(R.id.btn_new_process).setOnClickListener {
            NewProcessAct.start(this@NewProcessAct)
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
//        val dataCount = MultiProcessDataStoreUtil.getData()
//        if (dataCount != null) {
//            MultiProcessDataStoreUtil.putData(dataCount - 1)
//        }
//
//        getDataCount()
    }

    fun getDataCount() {
        val dataCount = MultiProcessDataStoreUtil.getData()

        if (dataCount != null) {

            var logStr = "新进程名字：" + SystemUtil.getProcessName(this) + "\n 数据:" + dataCount

            Log.d(TAG, logStr)

            textView?.setText(logStr)
        }
    }


    fun addDataStore() {
        val dataCount = DataStoreUtil.getData("AAA", 0);
        if (dataCount != null) {
            DataStoreUtil.putData("AAA", dataCount + 1)
        }

        var logStr = "DataStoreUtil 数据:" + dataCount
        Log.d(TAG, logStr)

        textView_ds?.setText(logStr)

    }

}