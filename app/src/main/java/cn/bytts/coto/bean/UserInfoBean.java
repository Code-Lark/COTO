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

package cn.bytts.coto.bean;

import java.util.List;

public class UserInfoBean {
    private long tag;
    private int grade;
    private String name;
    private int point;
    private String sex;
    private int age;
    private List<String> hobby;
    private int award;
    private int exp;
    private String desc;
    private String headPath;

    public void setTag(long tag) {
        this.tag = tag;
    }

    public long getTag() {
        return tag;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getAward() {
        return award;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getHeadPath() {
        return headPath;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "tag=" + tag +
                ", grade=" + grade +
                ", name='" + name + '\'' +
                ", point=" + point +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", hobby=" + hobby +
                ", award=" + award +
                ", exp=" + exp +
                ", desc='" + desc + '\'' +
                ", headPath='" + headPath + '\'' +
                '}';
    }
}
