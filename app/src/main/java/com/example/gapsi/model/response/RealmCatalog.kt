package com.example.gapsi.model.response

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RealmCatalog (
    var results: RealmList<ResultsRealm> = RealmList()
): RealmModel {}