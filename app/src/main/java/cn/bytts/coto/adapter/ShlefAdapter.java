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

package cn.bytts.coto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.label.LabelImageView;

import cn.bytts.coto.R;

import static com.xuexiang.xui.XUI.getContext;

public class ShlefAdapter extends BaseAdapter {
    int []data;
    String[]name;
    public ShlefAdapter(int[] data, String[] name){
        this.data=data;
        this.name=name;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup arg2) {
        // TODO Auto-generated method stub

        contentView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_book_view_list_item, null);

        LabelImageView view=(LabelImageView) contentView.findViewById(R.id.imageView1);
        if(data.length>position){
            if(position<name.length){
                //TODO 设置书籍内容
                //view.setText(name[position]);
            }
            view.setBackgroundResource(data[position]);
        }else{
            view.setBackgroundResource(data[0]);
            view.setClickable(false);
            view.setVisibility(View.INVISIBLE);
        }

        return contentView;
    }

}
