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

package cn.bytts.coto.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bytts.coto.base.BaseDAO;
import cn.bytts.coto.db.HobbyDBHelper;
import cn.bytts.coto.db.UserDBHelper;

public class HobbyDAO extends BaseDAO<Integer> {
    private static final String TAG = "HobbyDAO";
    private final String[] TABLE_COLUMNS = new String[]{"Id","Hobby", "isCheck"};
    private HobbyDBHelper hobbyDBHelper;
    private Context context;

    public HobbyDAO(Context context) {
        this.context = context;
        hobbyDBHelper = new HobbyDBHelper(this.context);
    }

    @Override
    public boolean isDataExist() {
        return false;
    }

    @Override
    public List<Integer> getAllData() {
        return null;
    }

    public ArrayList getAllDataInt(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = hobbyDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(HobbyDBHelper.TABLE_NAME, TABLE_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                ArrayList arrayList=new ArrayList();
                while (cursor.moveToNext()) {
                    if(cursor.getInt(cursor.getColumnIndex("isCheck"))==1){
                        arrayList.add(cursor.getInt(cursor.getColumnIndex("Id")));
                    }
                }
                return arrayList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    @Override
    public Integer getOneData(long tag) {
        return null;
    }

    @Override
    public boolean insertData(Integer integer) {
        return false;
    }

    @Override
    public boolean deleteData(Integer integer) {
        return false;
    }

    @Override
    public boolean updateData(Integer integer) {
        return false;
    }

    public boolean updateData(String hobbyname,Integer integer){
        SQLiteDatabase db = null;
        try {
            db = hobbyDBHelper.getWritableDatabase();

            db.beginTransaction();
            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("isCheck", integer);
            db.update(UserDBHelper.TABLE_NAME,
                    cv,
                    "Hobby = ?",
                    new String[]{hobbyname});
            db.setTransactionSuccessful();

            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    @Override
    public Integer parseData(Cursor cursor) {
        return null;
    }


}
