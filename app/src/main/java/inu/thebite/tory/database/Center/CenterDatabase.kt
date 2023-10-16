package inu.thebite.tory.database.Center

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CenterEntity::class], version = 1, exportSchema = false)
abstract class CenterDatabase : RoomDatabase() {
    abstract fun centerDao(): CenterDao
}