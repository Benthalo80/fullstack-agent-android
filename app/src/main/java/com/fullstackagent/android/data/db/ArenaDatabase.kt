package com.fullstackagent.android.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LeaderboardEntryEntity::class, VoteEntity::class], version = 1, exportSchema = false)
abstract class ArenaDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao
    abstract fun voteDao(): VoteDao

    companion object {
        @Volatile private var INSTANCE: ArenaDatabase? = null
        fun get(ctx: Context): ArenaDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(ctx.applicationContext, ArenaDatabase::class.java, "arena.db")
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}
