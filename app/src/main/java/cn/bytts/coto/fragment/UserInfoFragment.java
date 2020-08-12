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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectChangeListener;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xutil.data.DateUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.Date;

import butterknife.BindView;
import cn.bytts.coto.R;
import cn.bytts.coto.activity.MainActivity;
import cn.bytts.coto.core.BaseFragment;
import cn.bytts.coto.utils.XToastUtils;
import me.iwf.photopicker.PhotoPicker;


@Page(name = "账号")
public class UserInfoFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {

    //    @BindView(R.id.menu_common)
//    SuperTextView menuCommon;
//    @BindView(R.id.menu_privacy)
//    SuperTextView menuPrivacy;
//    @BindView(R.id.menu_push)
//    SuperTextView menuPush;
    @BindView(R.id.menu_userInfo)
    SuperTextView menuUserInfo;
    @BindView(R.id.menu_grade)
    SuperTextView menuGrade;
    @BindView(R.id.menu_coin)
    SuperTextView menucoin;
    @BindView(R.id.menu_support)
    SuperTextView menuSupport;
    @BindView(R.id.menu_age)
    SuperTextView menuAge;
    @BindView(R.id.menu_sex)
    SuperTextView menusex;
    @BindView(R.id.menu_hobby)
    SuperTextView menuHobby;
    @BindView(R.id.menu_savechange)
    SuperTextView menuSavaChange;

    private TimePickerView mDatePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initViews() {
        menuUserInfo.setOnSuperTextViewClickListener(this);
        menuGrade.setOnSuperTextViewClickListener(this);
        menucoin.setOnSuperTextViewClickListener(this);
        menuSupport.setOnSuperTextViewClickListener(this);
        menuAge.setOnSuperTextViewClickListener(this);
        menusex.setOnSuperTextViewClickListener(this);
        menuHobby.setOnSuperTextViewClickListener(this);
        menuSavaChange.setOnSuperTextViewClickListener(this);


//        // TODO: 2019-10-09 初始化数据
//        RadiusImageView ivAvatar = findViewById(R.id.iv_avatar);
//        TextView tvAvatar = findViewById(R.id.tv_avatar);
//        TextView tvSign = findViewById(R.id.tv_sign);
//        ivAvatar.setImageResource(R.drawable.ic_default_head);
//        tvAvatar.setText(R.string.app_name);
//        tvSign.setText("这个家伙很懒，什么也没有留下～～");
    }


    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.menu_userInfo:
                //TODO 修改头像
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .start(getContext(), this);
                break;
            case R.id.menu_grade:
                //查看经验
                showGrade();
                break;
            case R.id.menu_coin:
                //DialogLoader.getInstance().showTipDialog(getContext(),R.drawable.ic_coin_24,"积分","积分数","确认");
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_coin_24)
                        .title("积分")
                        .content("积分数")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.menu_support:
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_baseline_favorite_24)
                        .title("收到的赞")
                        .content("赞数")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.menu_age:
                //修改年龄
                showCalendar();
                break;
            case R.id.menu_sex:
                //修改性别
                String []items=new String[]{"男", "女"};
                showContextMenuDialog(getContext(), "性别", items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                DialogLoader.getInstance().showTipDialog(MainActivity.getTopActivity(),"提示","确认修改用户信息","确认");
                break;
            default:
                break;
        }
    }


    private void showGrade(){
        MaterialDialog dialog=new MaterialDialog.Builder(getContext())
                .title("等级：")
                .content("经验值：")
                .contentGravity(GravityEnum.START)
                .progress(false, 150, true)
                .negativeText("确认")
                .show();
        dialog.setProgress(30);
    }

    private void showCalendar(){
        if (mDatePicker == null) {
            mDatePicker = new TimePickerBuilder(getContext(), (date, v) -> XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMdd.get())))
                    .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                    .setTitleText("日期选择")
                    .build();
        }
        mDatePicker.show();
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
        new MaterialDialog.Builder(getContext())
                .title("偏好")
                .items(R.array.hobby)
                .itemsCallbackMultiChoice(
                        new Integer[]{0, 1},
                        (dialog, which, text) -> {
                            StringBuilder sb = new StringBuilder("选中：\n");
                            for (int i = 0; i < which.length; i ++){
                                sb.append(which[i]).append(":").append(text[i]).append("\n");
                            }
                            XToastUtils.toast(sb.toString());
                            return true;
                        })
                .positiveText(R.string.gm_confirm)
                .negativeText(R.string.gm_cancel)
                .show();
    }
}