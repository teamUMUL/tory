package inu.thebite.tory

import android.app.Application
import androidx.room.Room
import inu.thebite.tory.database.education.EducationDatabase
import inu.thebite.tory.repositories.education.EducationRepo
import inu.thebite.tory.repositories.education.EducationRepoImpl

import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(viewModelModule)
            modules(module {
                    //교육 기록
                single {
                    Room
                        .databaseBuilder(this@KoinApp, EducationDatabase::class.java, "education")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                single {
                    EducationRepoImpl(database = get())
                } bind EducationRepo::class

//                //STO
//                single {
//                    Room
//                        .databaseBuilder(this@KoinApp, STODatabase::class.java, "sto")
//                        .fallbackToDestructiveMigration()
//                        .build()
//                }
//                single {
//                    STORepoImpl(database = get())
//                } bind STORepo::class
//
//                //LTO
//                single {
//                    Room
//                        .databaseBuilder(this@KoinApp, LTODatabase::class.java, "lto")
//                        .fallbackToDestructiveMigration()
//                        .build()
//                }
//                single {
//                    LTORepoImpl(database = get())
//                } bind LTORepo::class
//
//                //센터
//                single {
//                    Room
//                        .databaseBuilder(this@KoinApp, CenterDatabase::class.java, "center")
//                        .build()
//                }
//                single {
//                    CenterRepoImpl(database = get())
//                } bind CenterRepo::class
//
//                //반
//                single {
//                    Room
//                        .databaseBuilder(this@KoinApp, ChildClassDatabase::class.java, "class")
//                        .build()
//                }
//                single {
//                    ChildClassRepoImpl(database = get())
//                } bind ChildClassRepo::class
//
//                //유아
//                single {
//                    Room
//                        .databaseBuilder(this@KoinApp, ChildInfoDatabase::class.java, "childInfo")
//                        .build()
//                }
//                single {
//                    ChildInfoRepoImpl(database = get())
//                } bind ChildInfoRepo::class
            })
        }
    }

}