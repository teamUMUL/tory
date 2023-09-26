package inu.thebite.tory.database.TypeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

class FloatListConverter {
    @TypeConverter
    fun listToJson(value: List<Float>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Float>? {
        return Gson().fromJson(value,Array<Float>::class.java)?.toList()
    }
}