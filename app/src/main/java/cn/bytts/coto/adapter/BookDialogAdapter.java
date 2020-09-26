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

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.widget.textview.label.LabelImageView;

import java.net.URL;

import cn.bytts.coto.R;
import cn.bytts.coto.bean.BookDatasBean;

import static com.xuexiang.xui.XUI.getContext;

public class BookDialogAdapter extends BaseAdapter {
    BookDatasBean bookData;

    public BookDialogAdapter(BookDatasBean bookDatasBean){
        bookData=bookDatasBean;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        contentView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_book_view_list, null);

        ImageView imageView=contentView.findViewById(R.id.book_image);
        TextView tvAuthor=contentView.findViewById(R.id.book_author);
        TextView tvBranch=contentView.findViewById(R.id.book_branch);
        TextView tvUpdateTime=contentView.findViewById(R.id.book_updateTime);
        TextView tvType =contentView.findViewById(R.id.book_type);

        imageView.setImageURI(Uri.parse("content://b.hiphotos.baidu.com/album/pic/item/9825bc315c6034a8dfa897e7ca13495409237609.jpg"));
        tvAuthor.setText(bookData.getBook().getAuthor());
        tvBranch.setText(bookData.getBook().getBranch());
        tvUpdateTime.setText(bookData.getBook().getUpdateTime());
        tvType.setText(bookData.getBook().getTypeOne()+" "+bookData.getBook().getTypeTwo()+" "+bookData.getBook().getTypeThree());

        return contentView;

    }
}
