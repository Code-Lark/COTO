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
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.List;

import cn.bytts.coto.base.BaseDAO;
import cn.bytts.coto.bean.UserInfoBean;
import cn.bytts.coto.db.UserDBHelper;
import cn.bytts.coto.db.UserInfoDBHelper;

public class UserInfoDAO extends BaseDAO<UserInfoBean> {
    private static final String TAG = "UserInfoDAO";
    private final String[] TABLE_COLUMNS = new String[]{"Tag", "Grade", "Name", "Point", "Sex", "Age", "Award", "Exp", "Description", "HeadPath"};
    private UserInfoDBHelper userInfoDBHelper;
    private Context context;

    public UserInfoDAO(Context context) {
        this.context = context;
        userInfoDBHelper = new UserInfoDBHelper(this.context);
    }

    @Override
    public boolean isDataExist() {
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = userInfoDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(UserInfoDBHelper.TABLE_NAME, new String[]{"COUNT(Tag)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    @Override
    public List<Integer> getAllData() {
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//
//        try {
//            db = userInfoDBHelper.getReadableDatabase();
//            // select * from Orders
//            cursor = db.query(UserDBHelper.TABLE_NAME, TABLE_COLUMNS, null, null, null, null, null);
//
//            if (cursor.getCount() > 0) {
//                List<UserInfoBean> userInfoList = new ArrayList<UserInfoBean>(cursor.getCount());
//                while (cursor.moveToNext()) {
//                    userInfoList.add(parseData(cursor));
//                }
//                return userInfoList;
//            }
//        }
//        catch (Exception e) {
//            Log.e(TAG, "", e);
//        }
//        finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
        return null;
    }

    @Override
    public boolean insertData(UserInfoBean userInfoBean) {
        SQLiteDatabase db = null;

        try {
            db = userInfoDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Tag", userInfoBean.getTag());
            contentValues.put("Grade", userInfoBean.getGrade());
            contentValues.put("Name", userInfoBean.getName());
            contentValues.put("Point", userInfoBean.getPoint());
            contentValues.put("Sex", userInfoBean.getSex());
            contentValues.put("Age", userInfoBean.getAge());
            contentValues.put("Award", userInfoBean.getAward());
            contentValues.put("Exp", userInfoBean.getExp());
            contentValues.put("Description", userInfoBean.getDesc());
            contentValues.put("HeadPath", userInfoBean.getHeadPath());

            db.insertOrThrow(UserInfoDBHelper.TABLE_NAME, null, contentValues);

            //设置事务标志为成功，当结束事务时就会提交事务
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "主键重复", e);
        } catch (Exception e) {
            Log.e(TAG, "添加失败", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 数据查询  此处将用户名为"Bor"的信息提取出来
     */
    @Override
    public UserInfoBean getOneData(long tag) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = userInfoDBHelper.getReadableDatabase();

            // select * from Orders where CustomName = 'Bor'
            cursor = db.query(UserInfoDBHelper.TABLE_NAME,
                    TABLE_COLUMNS,
                    "Tag = ?",
                    new String[]{tag + ""},
                    null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                UserInfoBean data = parseData(cursor);
                return data;
            }
        } catch (
                Exception e) {
            Log.e(TAG, "getOneData", e);
        } finally {
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
    public boolean deleteData(UserInfoBean userInfoBean) {
        return false;
    }

    @Override
    public boolean updateData(UserInfoBean userInfoBean) {
        SQLiteDatabase db = null;
        try {
            db = userInfoDBHelper.getWritableDatabase();

            db.beginTransaction();
            ContentValues cv = new ContentValues();

            cv.put("Name", userInfoBean.getName());
            cv.put("Sex", userInfoBean.getSex());
            cv.put("Age", userInfoBean.getAge());
            cv.put("Description", userInfoBean.getDesc());
            cv.put("HeadPath", userInfoBean.getHeadPath());

            db.update(UserInfoDBHelper.TABLE_NAME,
                    cv,
                    "Tag = ?",
                    new String[]{userInfoBean.getTag() + ""});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "updateData", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    @Override
    public UserInfoBean parseData(Cursor cursor) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setTag(cursor.getLong(cursor.getColumnIndex("Tag")));
        userInfoBean.setGrade(cursor.getInt(cursor.getColumnIndex("Grade")));
        userInfoBean.setName(cursor.getString(cursor.getColumnIndex("Name")));
        userInfoBean.setPoint(cursor.getInt(cursor.getColumnIndex("Point")));
        userInfoBean.setSex(cursor.getString(cursor.getColumnIndex("Sex")));
        userInfoBean.setAge(cursor.getInt(cursor.getColumnIndex("Age")));
        userInfoBean.setAward(cursor.getInt(cursor.getColumnIndex("Award")));
        userInfoBean.setExp(cursor.getInt(cursor.getColumnIndex("Exp")));
        userInfoBean.setDesc(cursor.getString(cursor.getColumnIndex("Description")));
        userInfoBean.setHeadPath(cursor.getString(cursor.getColumnIndex("HeadPath")));

        return userInfoBean;
    }
}
