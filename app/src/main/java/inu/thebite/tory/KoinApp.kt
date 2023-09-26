package inu.thebite.tory

import android.app.Application
import androidx.room.Room
import inu.thebite.tory.database.STODatabase
import inu.thebite.tory.repositories.STORepo
import inu.thebite.tory.repositories.STORepoImpl
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
                        .build()
                }
                single {
                    STORepoImpl(database = get())
                } bind STORepo::class
            })
        }
    }

}