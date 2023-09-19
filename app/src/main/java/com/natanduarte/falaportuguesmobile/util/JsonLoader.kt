package com.natanduarte.falaportuguesmobile.util

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class JsonLoader(private val context: Context) {

    fun loadJsonFileToObject(fileName: String): JSONObject? {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            return JSONObject(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadJsonFileToArray(fileName: String): JSONArray? {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            return JSONArray(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}

