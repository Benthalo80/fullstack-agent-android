package com.fullstackagent.android.data.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LeaderboardDao_Impl implements LeaderboardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LeaderboardEntryEntity> __insertionAdapterOfLeaderboardEntryEntity;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public LeaderboardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLeaderboardEntryEntity = new EntityInsertionAdapter<LeaderboardEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `leaderboard_entries` (`id`,`arena`,`category`,`rank`,`displayName`,`provider`,`openRouterId`,`elo`,`winRate`,`avgGenMs`,`fetchedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LeaderboardEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getArena());
        statement.bindString(3, entity.getCategory());
        statement.bindLong(4, entity.getRank());
        statement.bindString(5, entity.getDisplayName());
        statement.bindString(6, entity.getProvider());
        if (entity.getOpenRouterId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getOpenRouterId());
        }
        statement.bindLong(8, entity.getElo());
        statement.bindDouble(9, entity.getWinRate());
        if (entity.getAvgGenMs() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getAvgGenMs());
        }
        statement.bindLong(11, entity.getFetchedAt());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM leaderboard_entries WHERE arena=? AND category=?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<LeaderboardEntryEntity> rows,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLeaderboardEntryEntity.insert(rows);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clear(final String arena, final String category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, arena);
        _argIndex = 2;
        _stmt.bindString(_argIndex, category);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClear.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LeaderboardEntryEntity>> observe(final String arena, final String category) {
    final String _sql = "SELECT * FROM leaderboard_entries WHERE arena=? AND category=? ORDER BY elo DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, arena);
    _argIndex = 2;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"leaderboard_entries"}, new Callable<List<LeaderboardEntryEntity>>() {
      @Override
      @NonNull
      public List<LeaderboardEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArena = CursorUtil.getColumnIndexOrThrow(_cursor, "arena");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRank = CursorUtil.getColumnIndexOrThrow(_cursor, "rank");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProvider = CursorUtil.getColumnIndexOrThrow(_cursor, "provider");
          final int _cursorIndexOfOpenRouterId = CursorUtil.getColumnIndexOrThrow(_cursor, "openRouterId");
          final int _cursorIndexOfElo = CursorUtil.getColumnIndexOrThrow(_cursor, "elo");
          final int _cursorIndexOfWinRate = CursorUtil.getColumnIndexOrThrow(_cursor, "winRate");
          final int _cursorIndexOfAvgGenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "avgGenMs");
          final int _cursorIndexOfFetchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "fetchedAt");
          final List<LeaderboardEntryEntity> _result = new ArrayList<LeaderboardEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaderboardEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArena;
            _tmpArena = _cursor.getString(_cursorIndexOfArena);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpRank;
            _tmpRank = _cursor.getInt(_cursorIndexOfRank);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProvider;
            _tmpProvider = _cursor.getString(_cursorIndexOfProvider);
            final String _tmpOpenRouterId;
            if (_cursor.isNull(_cursorIndexOfOpenRouterId)) {
              _tmpOpenRouterId = null;
            } else {
              _tmpOpenRouterId = _cursor.getString(_cursorIndexOfOpenRouterId);
            }
            final int _tmpElo;
            _tmpElo = _cursor.getInt(_cursorIndexOfElo);
            final double _tmpWinRate;
            _tmpWinRate = _cursor.getDouble(_cursorIndexOfWinRate);
            final Long _tmpAvgGenMs;
            if (_cursor.isNull(_cursorIndexOfAvgGenMs)) {
              _tmpAvgGenMs = null;
            } else {
              _tmpAvgGenMs = _cursor.getLong(_cursorIndexOfAvgGenMs);
            }
            final long _tmpFetchedAt;
            _tmpFetchedAt = _cursor.getLong(_cursorIndexOfFetchedAt);
            _item = new LeaderboardEntryEntity(_tmpId,_tmpArena,_tmpCategory,_tmpRank,_tmpDisplayName,_tmpProvider,_tmpOpenRouterId,_tmpElo,_tmpWinRate,_tmpAvgGenMs,_tmpFetchedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
