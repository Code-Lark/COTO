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

public class BookBean {
    private int id;
    private String name;
    private String author;
    private String desc;
    private String updateTime;
    private String typeOne;
    private String typeTwo;
    private String typeThree;
    private String coverPath;
    private int awardSum;
    private long maxWord;
    private int node;
    private int branch;
    private int awardMouth;
    private int point;
    private boolean end;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setTypeOne(String typeOne) {
        this.typeOne = typeOne;
    }

    public String getTypeOne() {
        return typeOne;
    }

    public void setTypeTwo(String typeTwo) {
        this.typeTwo = typeTwo;
    }

    public String getTypeTwo() {
        return typeTwo;
    }

    public void setTypeThree(String typeThree) {
        this.typeThree = typeThree;
    }

    public String getTypeThree() {
        return typeThree;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setAwardSum(int awardSum) {
        this.awardSum = awardSum;
    }

    public int getAwardSum() {
        return awardSum;
    }

    public void setMaxWord(long maxWord) {
        this.maxWord = maxWord;
    }

    public long getMaxWord() {
        return maxWord;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getNode() {
        return node;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getBranch() {
        return branch;
    }

    public void setAwardMouth(int awardMouth) {
        this.awardMouth = awardMouth;
    }

    public int getAwardMouth() {
        return awardMouth;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean getEnd() {
        return end;
    }

}
