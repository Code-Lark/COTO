/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.bytts.coto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.bytts.coto.R;

public class HobbyDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = R.string.db_version;
    private static final String DB_NAME = "hobbyMsg.db";
    public static final String TABLE_NAME = "hobby";
    private static final String TAG = "HobbyDBHelper";


    public HobbyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public HobbyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库sql语句 并 执行
        String sql = "create table if not exists "+TABLE_NAME+"(Id integer primary key," +
                "Hobby text not null," +
                "isCheck integer not null)";
        db.execSQL(sql);

        try {
            db.beginTransaction();

            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (0,'玄幻', 0)");
            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (1,'武侠', 0)");
            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (2,'都市', 0)");
            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (3,'言情', 0)");
            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (4,'漫改', 0)");
            db.execSQL("insert into " + TABLE_NAME + " (Id,Hobby, isCheck) values (5,'科幻', 0)");

            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
