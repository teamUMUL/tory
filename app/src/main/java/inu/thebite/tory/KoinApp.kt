package inu.thebite.tory

import android.app.Application

import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(viewModelModule)
//            modules(module {
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
//            })
        }
    }

}