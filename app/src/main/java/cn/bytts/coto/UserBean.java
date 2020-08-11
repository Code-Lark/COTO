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

package cn.bytts.coto;

public class UserBean {
    //{"msg":"{\"tag\":100001,\"email\":\"1353092684@qq.com\",\"password\":\"123456\"}","code":200,"success":true}
    //{"code":200,"message":"success","data":{"tag":100001,"email":"1353092684@qq.com","password":"123456"}}
    private long tag;
    private String email;
    private String password;

    public void setTag(long tag) {
        this.tag = tag;
    }

    public long getTag() {
        return tag;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
