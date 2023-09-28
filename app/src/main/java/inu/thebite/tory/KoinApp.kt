package inu.thebite.tory

import android.app.Application
import androidx.room.Room
import inu.thebite.tory.database.LTO.LTODatabase
import inu.thebite.tory.database.STO.STODatabase
import inu.thebite.tory.repositories.LTO.LTORepo
import inu.thebite.tory.repositories.LTO.LTORepoImpl
import inu.thebite.tory.repositories.STO.STORepo
import inu.thebite.tory.repositories.STO.STORepoImpl
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single {
                    Room
                        .databaseBuilder(this@KoinApp, STODatabase::class.java, "sto")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                single {
                    STORepoImpl(database = get())
                } bind STORepo::class

                single {
                    Room
                        .databaseBuilder(this@KoinApp, LTODatabase::class.java, "lto")
                        .build()
                }
                single {
                    LTORepoImpl(database = get())
                } bind LTORepo::class
            })
        }
    }

}