package inu.thebite.tory

import android.app.Application
import androidx.room.Room
import inu.thebite.tory.database.Center.CenterDatabase
import inu.thebite.tory.database.ChildClass.ChildClassDatabase
import inu.thebite.tory.database.ChildInfo.ChildInfoDatabase
import inu.thebite.tory.database.LTO.LTODatabase
import inu.thebite.tory.database.STO.STODatabase
import inu.thebite.tory.repositories.Center.CenterRepo
import inu.thebite.tory.repositories.Center.CenterRepoImpl
import inu.thebite.tory.repositories.ChildClass.ChildClassRepo
import inu.thebite.tory.repositories.ChildClass.ChildClassRepoImpl
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepo
import inu.thebite.tory.repositories.ChildInfo.ChildInfoRepoImpl
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
                //STO
                single {
                    Room
                        .databaseBuilder(this@KoinApp, STODatabase::class.java, "sto")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                single {
                    STORepoImpl(database = get())
                } bind STORepo::class

                //LTO
                single {
                    Room
                        .databaseBuilder(this@KoinApp, LTODatabase::class.java, "lto")
                        .build()
                }
                single {
                    LTORepoImpl(database = get())
                } bind LTORepo::class

                //센터
                single {
                    Room
                        .databaseBuilder(this@KoinApp, CenterDatabase::class.java, "center")
                        .build()
                }
                single {
                    CenterRepoImpl(database = get())
                } bind CenterRepo::class

                //반
                single {
                    Room
                        .databaseBuilder(this@KoinApp, ChildClassDatabase::class.java, "class")
                        .build()
                }
                single {
                    ChildClassRepoImpl(database = get())
                } bind ChildClassRepo::class

                //유아
                single {
                    Room
                        .databaseBuilder(this@KoinApp, ChildInfoDatabase::class.java, "childInfo")
                        .build()
                }
                single {
                    ChildInfoRepoImpl(database = get())
                } bind ChildInfoRepo::class
            })
        }
    }

}