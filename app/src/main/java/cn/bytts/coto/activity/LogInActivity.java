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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.IOException;

import cn.bytts.coto.DAO.UserDAO;
import cn.bytts.coto.bean.JsonBean;
import cn.bytts.coto.R;
import cn.bytts.coto.bean.UserBean;
import cn.bytts.coto.httpservice.HttpUtils;
import cn.bytts.coto.utils.StrUtils;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="LogInActivity" ;
    private SuperTextView etEmail;
    private SuperTextView etPassword;
    private Button btLogin;
    private TextView btRegister;
    private String strEmail;
    private String strPassword;
    private String url="http://www.coto.bytts.cn/user";
    private UserBean userBean;
    private UserDAO userDAO=new UserDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initview();
    }

    //初始化View
    private void initview(){
        etEmail=findViewById(R.id.et_login_userName);
        etPassword=findViewById(R.id.et_login_password);
        btLogin=findViewById(R.id.bt_login_login);
        btRegister=findViewById(R.id.bt_login_register);

        //设置监听
        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_login:
                //TODO
                requestLogin();
                break;
            case R.id.bt_login_register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void requestLogin() {
        Log.i(TAG,"请求登陆");
        //strEmail=etEmail.getText().toString().trim();
        //strPassword=etPassword.getText().toString().trim();
        strEmail="1353092684@qq.com";
        strPassword="123456";

        if(strPassword.isEmpty()||strEmail.isEmpty()){
            XToast.warning(this,"账号或密码不能为空").show();
        }else if(!StrUtils.isEmail(strEmail)){
            XToast.warning(this,"请输入正确的邮箱").show();
            return;
        }else {
            Log.i(TAG,"postRequest");
            postRequest(strEmail,strPassword);
        }
    }

    /**
     * post请求后台
     * @param username
     * @param password
     */
    private void postRequest(String username,String password)  {
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                //String user = httpUtils.bolwingJson(username);
                Log.d(TAG, "url:" + url+"?email="+strEmail);

                try {
                    final String result = httpUtils.login(strEmail);
                    Log.d(TAG, "结果:" + result);

                    Gson gson=new Gson();
                    JsonBean jsonBean=gson.fromJson(result, JsonBean.class);

                    Log.d(TAG, "JsonBean: "+jsonBean);
                    userBean=jsonBean.getData();

                    Log.d(TAG, "Usergson: "+ userBean);

                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO
                            if(password.equals(userBean.getPassword())){
                                userDAO.insertDate(userBean);

                                XToast.success(LogInActivity.this,"登录成功").show();
                                Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                XToast.warning(LogInActivity.this,"登录失败").show();
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