package com.xb88.rider.data.local

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Provider

/**
 * Room callback that writes [SeedData] on first launch. It runs on every open (which also
 * follows first creation) and only inserts into empty tables, so an interrupted seed is
 * completed on the next launch and rows are never duplicated.
 */
class DatabaseSeeder(
    private val database: Provider<XB88Database>,
    private val scope: CoroutineScope,
) : RoomDatabase.Callback() {

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        scope.launch { seedIfEmpty(database.get()) }
    }

    private suspend fun seedIfEmpty(db: XB88Database) = db.withTransaction {
        if (db.bikeProfileDao().count() == 0) db.bikeProfileDao().insert(SeedData.bikeProfile)
        if (db.serviceTaskDao().count() == 0) db.serviceTaskDao().insertAll(SeedData.serviceTasks)
    }
}
