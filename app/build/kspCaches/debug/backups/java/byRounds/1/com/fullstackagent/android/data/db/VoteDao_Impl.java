package com.fullstackagent.android.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VoteDao_Impl implements VoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VoteEntity> __insertionAdapterOfVoteEntity;

  public VoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVoteEntity = new EntityInsertionAdapter<VoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `votes` (`id`,`battleId`,`arena`,`category`,`winner`,`loser`,`createdAt`,`synced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoteEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getBattleId());
        statement.bindString(3, entity.getArena());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getWinner());
        statement.bindString(6, entity.getLoser());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getSynced());
      }
    };
  }

  @Override
  public Object insert(final VoteEntity vote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVoteEntity.insert(vote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object unsynced(final Continuation<? super List<VoteEntity>> $completion) {
    final String _sql = "SELECT * FROM votes WHERE synced=0 ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VoteEntity>>() {
      @Override
      @NonNull
      public List<VoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBattleId = CursorUtil.getColumnIndexOrThrow(_cursor, "battleId");
          final int _cursorIndexOfArena = CursorUtil.getColumnIndexOrThrow(_cursor, "arena");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfWinner = CursorUtil.getColumnIndexOrThrow(_cursor, "winner");
          final int _cursorIndexOfLoser = CursorUtil.getColumnIndexOrThrow(_cursor, "loser");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final List<VoteEntity> _result = new ArrayList<VoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBattleId;
            _tmpBattleId = _cursor.getString(_cursorIndexOfBattleId);
            final String _tmpArena;
            _tmpArena = _cursor.getString(_cursorIndexOfArena);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpWinner;
            _tmpWinner = _cursor.getString(_cursorIndexOfWinner);
            final String _tmpLoser;
            _tmpLoser = _cursor.getString(_cursorIndexOfLoser);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpSynced;
            _tmpSynced = _cursor.getInt(_cursorIndexOfSynced);
            _item = new VoteEntity(_tmpId,_tmpBattleId,_tmpArena,_tmpCategory,_tmpWinner,_tmpLoser,_tmpCreatedAt,_tmpSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
