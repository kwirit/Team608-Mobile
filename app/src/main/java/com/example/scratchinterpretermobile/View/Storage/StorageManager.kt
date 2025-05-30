package com.example.scratchinterpretermobile.View.Storage

import android.content.Context
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.google.gson.Gson

class StorageManager private constructor(private val context: Context) {

    companion object {
        private var instance: StorageManager? = null

        fun init(context: Context) {
            instance = StorageManager(context.applicationContext)
        }

        val shared: StorageManager
            get() = instance ?: throw IllegalStateException("StorageManager not initialized")
    }

    private val gson by lazy { Gson() }

    fun saveBoxes(boxes: List<ProgramBox>) {
        val dtoList = boxes.map { it.toDTO() }
        val json = gson.toJson(dtoList)

        context.getSharedPreferences("app_data", Context.MODE_PRIVATE).edit().apply {
            putString("boxes", json)
            apply()
        }
    }

    fun loadBoxes(): List<ProgramBoxDTO>? {
        val json = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
            .getString("boxes", null)
        return json?.let {
            gson.fromJson(it, Array<ProgramBoxDTO>::class.java)?.toList()
        }
    }
}