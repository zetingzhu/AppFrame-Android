package com.zzt.datastore

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream

/**
 * @author: zeting
 * @date: 2025/4/8
 *
 */
object ProcessCountSerializer : Serializer<ProcessCountObj> {
    private val gson = Gson()

    override val defaultValue: ProcessCountObj
        get() = ProcessCountObj(0)

//    override suspend fun readFrom(input: InputStream): ProcessCountObj {
//        try {
//            ObjectInputStream(input).use {
//                return it.readObject() as ProcessCountObj
//            }
//        } catch (e: Exception) {
//            throw RuntimeException("Failed to read User from input stream", e)
//        }
//    }
//
//    override suspend fun writeTo(t: ProcessCountObj, output: OutputStream) {
//        try {
//            ObjectOutputStream(output).use {
//                it.writeObject(t)
//            }
//        } catch (e: Exception) {
//            throw RuntimeException("Failed to write User to output stream", e)
//        }
//    }

    override suspend fun readFrom(input: InputStream): ProcessCountObj {
        return try {
            val json = input.bufferedReader().use { it.readText() }
            gson.fromJson(json, ProcessCountObj::class.java)
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: ProcessCountObj, output: OutputStream) {
        val json = gson.toJson(t)
        output.bufferedWriter().use { it.write(json) }
    }
}