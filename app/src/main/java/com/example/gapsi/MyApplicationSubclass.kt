package com.example.gapsi

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplicationSubclass : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("movies.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}