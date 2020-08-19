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

package cn.bytts.coto.base;

import android.database.Cursor;
import java.util.List;

/**
 *
 * @param <T> jsonbean
 */
public abstract class BaseDAO<T> {
    /**
     * 判断表中是否有数据
     */
    public abstract boolean isDataExist();

    /**
     * 查询数据库中所有数据
     * @return
     */
    public abstract List<Integer> getAllData();

    /**
     * 数据查询  此处将Tag为"tag"的信息提取出来
     */
    public abstract T getOneData(long tag);

    /**
     * 新增一条数据
     */
    public abstract boolean insertData(T t);

    /**
     * 删除一条数据  此处删除Id为7的数据
     */
    public abstract boolean deleteData(T t);

    /**
     * 修改一条数据  此处将Tag的数据的 某值 修改了
     * 修改密码
     */
    public abstract boolean updateData(T t);

    /**
     * 将查找到的数据转换成T类
     */
    public abstract T parseData(Cursor cursor);
}
