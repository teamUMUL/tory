//package inu.thebite.tory.database.TypeConverter
//
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import java.util.Date
//
//class DateListConverter {
//
//    @TypeConverter
//    fun listToJson(value: List<Date>?): String? {
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    fun jsonToList(value: String): List<Date>? {
//        return Gson().fromJson(value,Array<Date>::class.java)?.toList()
//    }
//}