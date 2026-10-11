package com.fullstackagent.android.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ArenaDatabase_Impl extends ArenaDatabase {
  private volatile LeaderboardDao _leaderboardDao;

  private volatile VoteDao _voteDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `leaderboard_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `arena` TEXT NOT NULL, `category` TEXT NOT NULL, `rank` INTEGER NOT NULL, `displayName` TEXT NOT NULL, `provider` TEXT NOT NULL, `openRouterId` TEXT, `elo` INTEGER NOT NULL, `winRate` REAL NOT NULL, `avgGenMs` INTEGER, `fetchedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `votes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `battleId` TEXT NOT NULL, `arena` TEXT NOT NULL, `category` TEXT NOT NULL, `winner` TEXT NOT NULL, `loser` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `synced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '69ee6352f08902bed9dfa57fdf6999bb')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `leaderboard_entries`");
        db.execSQL("DROP TABLE IF EXISTS `votes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLeaderboardEntries = new HashMap<String, TableInfo.Column>(11);
        _columnsLeaderboardEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("arena", new TableInfo.Column("arena", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("rank", new TableInfo.Column("rank", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("provider", new TableInfo.Column("provider", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("openRouterId", new TableInfo.Column("openRouterId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("elo", new TableInfo.Column("elo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("winRate", new TableInfo.Column("winRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("avgGenMs", new TableInfo.Column("avgGenMs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLeaderboardEntries.put("fetchedAt", new TableInfo.Column("fetchedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLeaderboardEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLeaderboardEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLeaderboardEntries = new TableInfo("leaderboard_entries", _columnsLeaderboardEntries, _foreignKeysLeaderboardEntries, _indicesLeaderboardEntries);
        final TableInfo _existingLeaderboardEntries = TableInfo.read(db, "leaderboard_entries");
        if (!_infoLeaderboardEntries.equals(_existingLeaderboardEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "leaderboard_entries(com.fullstackagent.android.data.db.LeaderboardEntryEntity).\n"
                  + " Expected:\n" + _infoLeaderboardEntries + "\n"
                  + " Found:\n" + _existingLeaderboardEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsVotes = new HashMap<String, TableInfo.Column>(8);
        _columnsVotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("battleId", new TableInfo.Column("battleId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("arena", new TableInfo.Column("arena", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("winner", new TableInfo.Column("winner", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("loser", new TableInfo.Column("loser", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVotes.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVotes = new TableInfo("votes", _columnsVotes, _foreignKeysVotes, _indicesVotes);
        final TableInfo _existingVotes = TableInfo.read(db, "votes");
        if (!_infoVotes.equals(_existingVotes)) {
          return new RoomOpenHelper.ValidationResult(false, "votes(com.fullstackagent.android.data.db.VoteEntity).\n"
                  + " Expected:\n" + _infoVotes + "\n"
                  + " Found:\n" + _existingVotes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "69ee6352f08902bed9dfa57fdf6999bb", "5d50d18fed3642aa31da188f06371a35");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "leaderboard_entries","votes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `leaderboard_entries`");
      _db.execSQL("DELETE FROM `votes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LeaderboardDao.class, LeaderboardDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VoteDao.class, VoteDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LeaderboardDao leaderboardDao() {
    if (_leaderboardDao != null) {
      return _leaderboardDao;
    } else {
      synchronized(this) {
        if(_leaderboardDao == null) {
          _leaderboardDao = new LeaderboardDao_Impl(this);
        }
        return _leaderboardDao;
      }
    }
  }

  @Override
  public VoteDao voteDao() {
    if (_voteDao != null) {
      return _voteDao;
    } else {
      synchronized(this) {
        if(_voteDao == null) {
          _voteDao = new VoteDao_Impl(this);
        }
        return _voteDao;
      }
    }
  }
}
