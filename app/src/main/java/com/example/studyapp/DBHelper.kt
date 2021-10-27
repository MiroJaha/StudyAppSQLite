package com.example.studyapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (
    context: Context?,
    name: String?= "Subjects.db",
    factory: SQLiteDatabase.CursorFactory?= null,
    version: Int= 2,
    private val tableName: String
) : SQLiteOpenHelper(context, name, factory, version) {

    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table Android (PK INTEGER PRIMARY KEY AUTOINCREMENT, Title Text, Material Text, Details Text)")
        p0?.execSQL("create table Kotlin (PK INTEGER PRIMARY KEY AUTOINCREMENT, Title Text, Material Text, Details Text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS Android")
        p0?.execSQL("DROP TABLE IF EXISTS Kotlin ")
        onCreate(p0)
    }

    fun saveData(data: Data): Long {
        val contentValue= ContentValues()
        contentValue.put("Title",data.title)
        contentValue.put("Material",data.material)
        contentValue.put("Details",data.details)
        return sqLiteDatabase.insert(tableName,null,contentValue)
    }

    fun gettingData(): ArrayList<Data>{
        val celebrity= arrayListOf<Data>()
        return try{
            val cursor =
                sqLiteDatabase.query(tableName, null, null, null, null, null, null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                celebrity.add(Data(
                    cursor.getInt(cursor.getColumnIndexOrThrow("PK")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Material")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Details"))
                )
                )
                cursor.moveToNext()
            }
            celebrity
        } catch (e:Exception){
            celebrity
        }
    }

    fun updateCelebrity(data: Data): Int{
        val contentValue= ContentValues()
        contentValue.put("Title",data.title)
        contentValue.put("Material",data.material)
        contentValue.put("Details",data.details)
        return sqLiteDatabase.update(tableName,contentValue,"PK = ?", arrayOf("${data.pk}"))
    }

    fun deleteCelebrity(pk: Int): Int{
        return sqLiteDatabase.delete(tableName,"PK=?", arrayOf("$pk"))
    }
}