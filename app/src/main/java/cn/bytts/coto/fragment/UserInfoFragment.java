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

package cn.bytts.coto.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bytts.coto.DAO.HobbyDAO;
import cn.bytts.coto.DAO.UserInfoDAO;
import cn.bytts.coto.R;
import cn.bytts.coto.activity.GloVar;
import cn.bytts.coto.bean.JsonBean;
import cn.bytts.coto.bean.UserInfoBean;
import cn.bytts.coto.core.BaseFragment;
import cn.bytts.coto.httpservice.HttpUtils;
import cn.bytts.coto.utils.XToastUtils;

import static com.xuexiang.xutil.XUtil.runOnUiThread;


@Page(name = "账号")
public class UserInfoFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener, View.OnClickListener {
    @BindView(R.id.menu_name)
    SuperTextView menuName;
    @BindView(R.id.menu_desc)
    SuperTextView menuDesc;
    @BindView(R.id.menu_grade)
    SuperTextView menuGrade;
    @BindView(R.id.menu_point)
    SuperTextView menuPoint;
    @BindView(R.id.menu_award)
    SuperTextView menuAward;
    @BindView(R.id.menu_age)
    SuperTextView menuAge;
    @BindView(R.id.menu_sex)
    SuperTextView menusex;
    @BindView(R.id.menu_hobby)
    SuperTextView menuHobby;
    @BindView(R.id.menu_savechange)
    SuperTextView menuSavaChange;
    @BindView(R.id.riv_head_pic)
    RadiusImageView rivHeadPic;

    private TimePickerView mDatePicker;
    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String[] mSexOption;
    private int sexSelectOption = 0;

    public UserInfoDAO userInfoDAO;
    private UserInfoBean userInfoBean = new UserInfoBean();

    HttpUtils httpUtils = new HttpUtils();
    private String TAG = "UserInfoFragment";

    protected Context mActivity;
    private HobbyDAO hobbyDAO;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initViews() {
        menuName.setOnSuperTextViewClickListener(this);
        menuDesc.setOnSuperTextViewClickListener(this);
        menuGrade.setOnSuperTextViewClickListener(this);
        menuPoint.setOnSuperTextViewClickListener(this);
        menuAward.setOnSuperTextViewClickListener(this);
        menuAge.setOnSuperTextViewClickListener(this);
        menusex.setOnSuperTextViewClickListener(this);
        menuHobby.setOnSuperTextViewClickListener(this);
        menuSavaChange.setOnSuperTextViewClickListener(this);
        rivHeadPic.setOnClickListener(this);

        Log.e(TAG, "..............");

//        RadiusImageView ivAvatar = findViewById(R.id.iv_avatar);
//        TextView tvAvatar = findViewById(R.id.tv_avatar);
//        TextView tvSign = findViewById(R.id.tv_sign);
//        ivAvatar.setImageResource(R.drawable.ic_default_head);
//        tvAvatar.setText(R.string.app_name);
//        tvSign.setText("这个家伙很懒，什么也没有留下～～");

        new Thread(new Runnable() {
            @Override
            public void run() {
                userInfoDAO = new UserInfoDAO(mActivity);
                //TODO:  初始化数据
                if (userInfoDAO.getOneData(GloVar.getTAG()) == null) {
                    //不存在数据，则联网查询
                    try {
                        final String result = httpUtils.get("userInfo", GloVar.getTAG());
                        Gson gson = new Gson();
                        JsonBean<UserInfoBean> jsonBean = gson.fromJson(result, new TypeToken<JsonBean<UserInfoBean>>() {
                        }.getType());

                        userInfoBean = jsonBean.getData();

                        userInfoDAO.insertData(userInfoBean);
                        Log.e(TAG, "....run..........");

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "initViews", e);
                    }
                }else{
                    //有数据，读取数据
                    userInfoBean=userInfoDAO.getOneData(GloVar.getTAG());
                }

                //更新UI,在UI线程中
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, ".......init.......");

                        menuName.setCenterBottomString(userInfoBean.getName());
                        menuDesc.setCenterBottomString(userInfoBean.getDesc());
                        menuGrade.setCenterBottomString(userInfoBean.getGrade() + "");
                        menuPoint.setCenterBottomString(userInfoBean.getPoint() + "");
                        menuAward.setCenterBottomString(userInfoBean.getAward() + "");
                        menuAge.setCenterBottomString(userInfoBean.getAge() + "");
                        menusex.setCenterBottomString(userInfoBean.getSex());
                    }
                });

            }
        }).start();

        mSexOption = new String[100];
        for (int i = 0; i < mSexOption.length; i++) {
            mSexOption[i] = (i + 1) + "";
        }

    }


    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.menu_name:
                showTextViewDialog("用户名",1, 6, menuName);
                break;
            case R.id.menu_desc:
                showTextViewDialog("简介", 0,255, menuDesc);
                break;
            case R.id.menu_grade:
                //查看经验
                showGrade();
                break;
            case R.id.menu_point:
                //DialogLoader.getInstance().showTipDialog(getContext(),R.drawable.ic_coin_24,"积分","积分数","确认");
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_coin_24)
                        .title("积分")
                        .content(userInfoBean.getPoint()+"")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.menu_award:
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_baseline_favorite_24)
                        .title("收到的赞")
                        .content(userInfoBean.getAward()+"")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.menu_age:
                //修改年龄
                showCalendar();
                break;
            case R.id.menu_sex:
                //修改性别
                String[] items = new String[]{"男", "女"};
                showContextMenuDialog(getContext(), "性别", items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        menusex.setCenterBottomString(items[which]);
                        XToastUtils.toast(superTextView.getLeftString());
                    }
                });
                break;
            case R.id.menu_hobby:
                //修改偏好
                //XToastUtils.toast(superTextView.getLeftString());
                showMultiChoiceDialog();
                break;
            case R.id.menu_savechange:
                //DialogLoader.getInstance().showTipDialog(MainActivity.getTopActivity(),"提示","确认修改用户信息","确认");
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_menu_issues)
                        .title("提示")
                        .content("确认修改用户信息?")
                        .positiveText("确认")
                        .onPositive((dialog, which) -> {
                            savechange();
                            XToastUtils.toast("dianji");
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    private boolean savechange() {
        //保存修改
        userInfoBean.setName(menuName.getCenterBottomString());
        userInfoBean.setDesc(menuDesc.getCenterBottomString());
        userInfoBean.setSex(menusex.getCenterBottomString());
        userInfoBean.setAge(Integer.parseInt(menuAge.getCenterBottomString()));
//      userInfoBean.setHeadPath("地址");

        //本地更新
        userInfoDAO.updateData(userInfoBean);
        //TODO：网络更新

        return true;
    }

    private void showGrade() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("等级：")
                .content("经验值：")
                .contentGravity(GravityEnum.START)
                .progress(false, 150, true)
                .negativeText("确认")
                .show();
        dialog.setProgress(30);
    }

    /**
     * 年龄
     */
    private void showCalendar() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            menuAge.setCenterBottomString(mSexOption[options1]);
            sexSelectOption = options1;
            return false;
        })
                .setTitleText("年龄")
                .setSelectOptions(sexSelectOption)
                .build();
        pvOptions.setPicker(mSexOption);
        pvOptions.show();
    }

    public Dialog showContextMenuDialog(Context context, String title, String[] items, final DialogInterface.OnClickListener listener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (listener != null) {
                            listener.onClick(dialog, position);
                        }
                    }
                })
                .show();
    }

    /**
     * 带多选项的Dialog
     */
    private void showMultiChoiceDialog() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                hobbyDAO = new HobbyDAO(mActivity);
                Integer []integers=(Integer[]) hobbyDAO.getAllDataInt().toArray();
                new MaterialDialog.Builder(getContext())
                        .title("偏好")
                        .items(R.array.hobby)
                        .itemsCallbackMultiChoice(
                                integers,
                                (dialog, which, text) -> {
                                    StringBuilder sb = new StringBuilder();
                                    int i;
                                    for (i = 0; i < which.length - 1; i++) {
                                        sb.append(text[i]).append("、");
                                    }
                                    if (i>0||which.length==1){
                                        sb.append(text[i]);
                                    }
                                    XToastUtils.toast(sb.toString());
                                    menuHobby.setCenterBottomString(sb.toString());
                                    return true;
                                })
                        .positiveText(R.string.gm_confirm)
                        .negativeText(R.string.gm_cancel)
                        .show();
            }
        }).start();

    }

    private void showTextViewDialog(String title,int minSize, int maxSize, SuperTextView superTextView) {
        new MaterialDialog.Builder(getContext())
                .title(title)
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(
                        "",
                        superTextView.getCenterBottomString(),
                        false,
                        ((dialog, input) -> XToastUtils.toast("修改成功")))
                .inputRange(minSize, maxSize)
                .positiveText(R.string.gm_confirm)
                .onPositive(((dialog, which) -> superTextView.setCenterBottomString(dialog.getInputEditText().getText().toString())))
                .show();
    }

    @Override
    public void onClick(View v) {
        //TODO 修改头像,看看能不能用好看的图片选择器
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }
}