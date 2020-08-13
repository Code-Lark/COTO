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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bytts.coto.UserDBHelper;
import cn.bytts.coto.bean.UserBean;

public class UserDAO {
    private static final String TAG = "UserDao";

    // 列定义
    private final String[] USER_COLUMNS = new String[] {"Tag", "Email","Password"};

    private Context context;
    private UserDBHelper userDBHelper;

    public UserDAO(Context context) {
        this.context = context;
        userDBHelper = new UserDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = userDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(UserDBHelper.TABLE_NAME, new String[]{"COUNT(Tag)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
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
        return false;
    }

    /**
     * 初始化数据
     */
//    public void initTable(){
//        SQLiteDatabase db = null;
//
//        try {
//            db = userDBHelper.getWritableDatabase();
//            db.beginTransaction();
//
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (1, 'Arc', 100, 'China')");
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (2, 'Bor', 200, 'USA')");
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (3, 'Cut', 500, 'Japan')");
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (4, 'Bor', 300, 'USA')");
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (5, 'Arc', 600, 'China')");
//            db.execSQL("insert into " + UserDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (6, 'Doom', 200, 'China')");
//
//            db.setTransactionSuccessful();
//        }catch (Exception e){
//            Log.e(TAG, "", e);
//        }finally {
//            if (db != null) {
//                db.endTransaction();
//                db.close();
//            }
//        }
//    }

    /**
     * 查询数据库中所有数据
     */
    public List<UserBean> getAllDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = userDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(UserDBHelper.TABLE_NAME, USER_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<UserBean> orderList = new ArrayList<UserBean>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parseOrder(cursor));
                }
                return orderList;
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

    /**
     * 新增一条数据
     */
    public boolean insertDate(UserBean userBean){
        SQLiteDatabase db = null;

        try {
            db = userDBHelper.getWritableDatabase();
            db.beginTransaction();

            // insert into Orders(Id, CustomName, OrderPrice, Country) values (7, "Jne", 700, "China");
            ContentValues contentValues = new ContentValues();
            contentValues.put("Tag", userBean.getTag());
            contentValues.put("Email", userBean.getEmail());
            contentValues.put("Password",userBean.getPassword());
            db.insertOrThrow(UserDBHelper.TABLE_NAME, null, contentValues);

            //设置事务标志为成功，当结束事务时就会提交事务
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "添加成功", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 删除一条数据  此处删除Id为7的数据
     */
    public boolean deleteOrder(UserBean userBean) {
        SQLiteDatabase db = null;

        try {
            db = userDBHelper.getWritableDatabase();

            db.beginTransaction();
            // delete from Orders where Id = 7
            //db.delete(UserDBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(7)});
            db.delete(UserDBHelper.TABLE_NAME, "Tag = ?", new String[]{userBean.getTag()+""});
            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            Log.e(TAG, "删除失败", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 修改一条数据  此处将Tag的数据的password修改了
     * 修改密码
     * @param userBean
     */
    public boolean updateOrder(UserBean userBean){
        SQLiteDatabase db = null;
        try {
            db = userDBHelper.getWritableDatabase();

            db.beginTransaction();
            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("Password", userBean.getPassword());
            db.update(UserDBHelper.TABLE_NAME,
                    cv,
                    "Tag = ?",
                    new String[]{userBean.getTag()+""});
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

    /**
     * 将查找到的数据转换成Order类
     */
    private UserBean parseOrder(Cursor cursor){
        UserBean userBean = new UserBean();
        userBean.setTag(cursor.getLong(cursor.getColumnIndex("Tag")));
        userBean.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
        userBean.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
        return userBean;
    }
}
