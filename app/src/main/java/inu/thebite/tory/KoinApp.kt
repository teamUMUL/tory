package inu.thebite.tory

import android.app.Application
import androidx.room.Room
import inu.thebite.tory.database.ChildDatabase
import inu.thebite.tory.database.GameDatabase
import inu.thebite.tory.repositories.ChildRepo
import inu.thebite.tory.repositories.ChildRepoImpl
import inu.thebite.tory.repositories.GameRepo
import inu.thebite.tory.repositories.GameRepoImpl
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
                        .databaseBuilder(this@KoinApp, GameDatabase::class.java, "db")
                        .build()
                }
                single {
                    GameRepoImpl(database = get())
                } bind GameRepo::class

                single {
                    Room
                        .databaseBuilder(this@KoinApp, ChildDatabase::class.java, "childDB")
                        .build()
                }
                single {
                    ChildRepoImpl(database = get())
                } bind ChildRepo::class
            })
        }
    }
}