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

package cn.bytts.coto.httpservice;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private RequestBody body;
    private String url="http://www.coto.bytts.cn/";


    public String login(String user) throws IOException {
        Request request = new Request.Builder()
                .url(url+"user"+"?email=" + user)
                .build();
        Response response = client.newCall(request).execute();

        String result = response.body().string();

        Log.i("http-get",result);
        return result;
    }

    /**
     * 通过get方法进行网络连接
     * @param table 要查询的表名
     * @param tag   带查询值的key
     * @return      string形式的json
     * @throws IOException
     */
    public String get(String table,long tag) throws IOException {
        Log.e("TAG", url+table+"?tag="+tag);
        Request request = new Request.Builder()
                .url(url+table+ "?tag=" + tag)
                .build();
        Response response = client.newCall(request).execute();

        String result = response.body().string();
        Log.e("TAG", result);

        return result;
    }




    public String register(String url, String user, String password) throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("email", user)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url + "?email=" + user)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        // string->toString
        String result = response.body().string();
        return result;
    }
}
