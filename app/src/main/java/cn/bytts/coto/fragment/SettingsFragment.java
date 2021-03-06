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

import cn.bytts.coto.core.BaseFragment;
import cn.bytts.coto.core.webview.AgentWebActivity;
import cn.bytts.coto.utils.XToastUtils;
import cn.bytts.coto.R;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xui.widget.toast.XToast;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2019-10-15 22:38
 */
@Page(name = "设置")
public class SettingsFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {

    @BindView(R.id.menu_common)
    SuperTextView menuCommon;
    @BindView(R.id.menu_privacy)
    SuperTextView menuPrivacy;
    @BindView(R.id.menu_push)
    SuperTextView menuPush;
    @BindView(R.id.menu_helper)
    SuperTextView menuHelper;
    @BindView(R.id.menu_logout)
    SuperTextView menuLogout;
    @BindView(R.id.menu_about)
    SuperTextView menuAbout;
    @BindView(R.id.menu_feedback)
    SuperTextView menuFeedback;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initViews() {
        menuCommon.setOnSuperTextViewClickListener(this);
        menuPrivacy.setOnSuperTextViewClickListener(this);
        menuPush.setOnSuperTextViewClickListener(this);
        menuHelper.setOnSuperTextViewClickListener(this);
        menuLogout.setOnSuperTextViewClickListener(this);
        menuAbout.setOnSuperTextViewClickListener(this);
        menuFeedback.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        switch(superTextView.getId()) {
            case R.id.menu_common:
                XToastUtils.toast("待完成");
                break;
            case R.id.menu_privacy:
                XToastUtils.toast("待完成");
                break;
            case R.id.menu_push:
                XToastUtils.toast("待完成");
                break;
            case R.id.menu_helper:
                XToastUtils.toast(superTextView.getLeftString());
                break;
            case R.id.menu_about:
                openNewPage(AboutFragment.class);
                break;
            case R.id.menu_feedback:
                //TODO: 反馈意见发送邮件
                XToastUtils.toast("待完成");
                AgentWebActivity.goWeb(getContext(), getString(R.string.url_feedback_link));
                break;
            case R.id.menu_logout:
                XToastUtils.toast(superTextView.getCenterString());
                break;
            default:
                break;
        }
    }
}
