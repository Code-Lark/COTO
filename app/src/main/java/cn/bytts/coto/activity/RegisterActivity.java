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

package cn.bytts.coto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.IOException;

import cn.bytts.coto.JsonBean;
import cn.bytts.coto.R;
import cn.bytts.coto.UserBean;
import cn.bytts.coto.utils.HttpUtils;
import cn.bytts.coto.utils.StrUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String url="http://www.coto.bytts.cn/user";
    private static final String TAG ="RegisterActivity" ;
    private Button btRegister;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etVerifyPassword;
    private String strEmail;
    private String strPassword;
    private String strVerifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        btRegister = findViewById(R.id.bt_register_register);
        etUsername = findViewById(R.id.et_register_userName);
        etPassword = findViewById(R.id.et_register_password);
        etVerifyPassword = findViewById(R.id.et_register_verifyPassword);

        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        strEmail=etUsername.getText().toString().trim();
//        strPassword=etPassword.getText().toString().trim();
//        strVerifyPassword=etVerifyPassword.getText().toString().trim();

        strEmail="13530926849@qq.com";
        strPassword="123456";
        strVerifyPassword="123456";
        //TODO
        if(strPassword.isEmpty()||strEmail.isEmpty()){
            XToast.warning(this,"账号或密码不能为空").show();
        }else if(!StrUtils.isEmail(strEmail)){
            XToast.warning(this,"请输入正确的邮箱").show();
            return;
        }else if(!strPassword.equals(strVerifyPassword)){
            Log.d(TAG, "onClick:email ");
            XToast.warning(this,"请确认输入密码一致").show();
        }else{
            postRequest(strEmail,strPassword);
        }
    }

    /**
     * post请求后台
     * @param username
     * @param password
     */
    private void postRequest(String username,String password)  {
        Log.d(TAG,"请求注册");


        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url("http://www.coto.bytts.cn/user")
                .post(formBody)
                .build();


        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                //String user = httpUtils.bolwingJson(username);
                Log.d(TAG, "url:" + url);
                try {
                    final String result = httpUtils.login(url, strEmail);
                    Log.d(TAG, "结果:" + result);

                    Gson gson=new Gson();
                    JsonBean jsonBean=gson.fromJson(result, JsonBean.class);
                    Log.d(TAG, "JsonBean: "+jsonBean.toString());
                    //UserBean userBean = gson.fromJson(jsonBean.getMsg(),UserBean.class);

                    //Log.d(TAG, "gson: "+userBean.toString());

                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO
                            if(!jsonBean.getMsg().equals("操作成功")){
                                Intent intent=new Intent(RegisterActivity.this,LogInActivity.class);
                                startActivity(intent);
                                XToast.success(RegisterActivity.this,"注册成功").show();
                                finish();
                            }else{
                                XToast.warning(RegisterActivity.this,"注册失败").show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}