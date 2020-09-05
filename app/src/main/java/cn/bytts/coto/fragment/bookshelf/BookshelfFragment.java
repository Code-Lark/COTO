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

package cn.bytts.coto.fragment.bookshelf;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import cn.bytts.coto.R;
import cn.bytts.coto.adapter.ShlefAdapter;
import cn.bytts.coto.adapter.base.delegate.SimpleDelegateAdapter;
import cn.bytts.coto.adapter.entity.NewInfo;
import cn.bytts.coto.core.BaseFragment;
import cn.bytts.coto.utils.Utils;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.List;

import static com.xuexiang.xutil.XUtil.getPackageManager;

/**
 * @author xuexiang
 * @since 2019-10-30 00:19
 */
@Page(name = "书架")
public class BookshelfFragment extends BaseFragment {
    @BindView(R.id.bookShelf)
    GridView bookShelf;

    private int[] data = {
            R.drawable.cover_txt,R.drawable.cover_txt,R.drawable.cover_txt,R.drawable.cover_txt

    };
    private String[] name={
            "天龙八部","搜神记","水浒传","黑道悲情"
    };


    private List<ResolveInfo> apps;


    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        bookShelf=findViewById(R.id.bookShelf);

        ShlefAdapter adapter=new ShlefAdapter(data,name);
        bookShelf.setAdapter(adapter);
        bookShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(arg2>=data.length){

                }else{
                    Toast.makeText(getContext(), ""+arg2, Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadApps();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        apps = getPackageManager().queryIntentActivities(intent, 0);
    }

}
