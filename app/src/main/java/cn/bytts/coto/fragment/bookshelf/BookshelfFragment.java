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

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.bytts.coto.DAO.UserInfoDAO;
import cn.bytts.coto.R;
import cn.bytts.coto.activity.GloVar;
import cn.bytts.coto.activity.ReadBook;
import cn.bytts.coto.adapter.BookDialogAdapter;
import cn.bytts.coto.adapter.ShlefAdapter;
import cn.bytts.coto.adapter.base.delegate.SimpleDelegateAdapter;
import cn.bytts.coto.adapter.entity.NewInfo;
import cn.bytts.coto.bean.BookBean;
import cn.bytts.coto.bean.BookDatasBean;
import cn.bytts.coto.bean.JsonBean;
import cn.bytts.coto.bean.UserInfoBean;
import cn.bytts.coto.core.BaseFragment;
import cn.bytts.coto.httpservice.HttpUtils;
import cn.bytts.coto.utils.FileCacheUtil;
import cn.bytts.coto.utils.Utils;
import cn.bytts.coto.utils.service.JsonSerializationService;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.CustomMaterialDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.statelayout.MultipleStatusView;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xui.XUI.getContext;
import static com.xuexiang.xutil.XUtil.getPackageManager;
import static com.xuexiang.xutil.XUtil.runOnUiThread;

/**
 * @author xuexiang
 * @since 2019-10-30 00:19
 */
@Page(name = "书架")
public class BookshelfFragment extends BaseFragment {
    @BindView(R.id.bookShelf)
    GridView bookShelf;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mMultipleStatusView;

    private int[] data = {
            R.drawable.cover_txt,R.drawable.cover_txt,R.drawable.cover_txt,R.drawable.cover_txt

    };
    private String[] name={
            "天龙八部","搜神记","水浒传","黑道悲情"
    };


    private List<ResolveInfo> apps;

    HttpUtils httpUtils = new HttpUtils();
    private String TAG = "BookshelfFragment";

    ShlefAdapter adapter;


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

        mMultipleStatusView.showContent();

        adapter = new ShlefAdapter(booklist);
        bookShelf.setAdapter(adapter);
        bookShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //书籍点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(arg2>=data.length){

                }else{
                    Intent intent=new Intent();
                    intent.setClass(getContext(), ReadBook.class);
                    startActivity(intent);

                }
            }

        });

        bookShelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"setOnItemLongClickListener");

                new MaterialDialog.Builder(getContext())
                        .content("是否删除")
                        .positiveText(R.string.lab_yes)
                        .negativeText(R.string.lab_no)
                        .onPositive((dialog, which) -> {
                            Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
                        })
                        .show();

                return true;
            }
        });

        loadApps();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        booklist=new ArrayList<>();

        String str=FileCacheUtil.getCache(getContext(),"bookShelf.txt");


        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();


//        BookDatasBean bookDatasBean=new BookDatasBean();
//        BookBean bookBean=new BookBean();
//        bookBean.setName("ddd");
//        bookDatasBean.setBook(bookBean);
//        booklist.add(bookDatasBean);

        GetBookShelf4Web getBookShelf4Web=new GetBookShelf4Web();
        Thread thread=new Thread(getBookShelf4Web,"th1");

        thread.start();
    }


    List<BookDatasBean> booklist;

    class GetBookShelf4Web implements  Runnable{
        @Override
        public void run() {
            try {

                final String result = httpUtils.get("bookShelf", 100001);

                FileCacheUtil.setCache(result,getContext(),"bookShelf.txt",Context.MODE_PRIVATE);

                Gson gson = new Gson();
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArr  =jsonObject.getJSONArray("data");// 接收JSON对象里的数组

                BookBean bookBean;
                String str;
                //解析json数组
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject jsonTemp = (JSONObject)jsonArr.getJSONObject(i);
                    BookDatasBean bookDatasBean=new BookDatasBean();


                    bookDatasBean.setPercent(Integer.parseInt(jsonTemp.getString("percent")));
                    bookDatasBean.setUpdate(Boolean.parseBoolean(jsonTemp.getString("update")));

                    str = jsonTemp.getString("book");
                    bookBean=gson.fromJson(str,BookBean.class);
                    bookDatasBean.setBook(bookBean);

                    booklist.add(bookDatasBean);
                }

                Log.i(TAG, "....run..........");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "initViews", e);
            }
            //更新UI,在UI线程中
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, ".......runOnUiThread.......");

                    adapter = new ShlefAdapter(booklist);
                    bookShelf.setAdapter(adapter);
                }
            });
        }
    }


    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        apps = getPackageManager().queryIntentActivities(intent, 0);
    }

}
