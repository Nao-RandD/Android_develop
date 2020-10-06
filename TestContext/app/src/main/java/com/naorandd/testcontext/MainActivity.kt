package com.naorandd.testcontext

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // アクティビティコンテキスト
        val activity: Context = this

        // アプリケーションコンテキスト
        val application = applicationContext

        // リストに表示するアイテム
        val item = arrayOf(
                "aaa", "bbb", "ccc", "ddd"
        )
        val activityList: ListView = findViewById<ListView>(R.id.listView1)
        val applicationList: ListView = findViewById<ListView>(R.id.listView2)

        // リスト表示用のArrayAdapterの作成
        val activityAdapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1, item)
        val applicationAdapter = ArrayAdapter(application,
                android.R.layout.simple_list_item_1, item)
        activityList.adapter = activityAdapter
        applicationList.adapter = applicationAdapter
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
}