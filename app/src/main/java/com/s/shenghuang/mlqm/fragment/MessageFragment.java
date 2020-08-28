package com.s.shenghuang.mlqm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.util.NetUtils;
import com.s.shenghuang.mlqm.MainActivity;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.activity.ChatActivity;
import com.s.shenghuang.mlqm.interfaces.Constants;
import com.s.shenghuang.mlqm.utils.MacUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-03-13
 * @params 消息会话列表
 **/
public class MessageFragment extends ConversationListFragment implements  View.OnClickListener {

    EaseUI easeUI;
    private View empty;
    private TextView errorText;
    private LinearLayout error_box;
    private FrameLayout systemLayout, appraiseLayout, orderLayout;
    private TextView systemNumberTv, appraiseNumberTv, orderNumberTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }
    public static MessageFragment newInstance(){
        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private int scrollYs=0;//刷新距离

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        super.initView();

        //空布局
        empty = View.inflate(getActivity(),R.layout.base_empty,null);
    }

    @Override
    protected void setUpView() {
        setTitleBar();
        super.setUpView();

        converSationAdapter.setEmptyView(empty);

        converSationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.list_itease_layout:
                        EMConversation conversation = conversationLists.get(position);
                        String username = conversation.conversationId();

                        if (username.equals(EMClient.getInstance().getCurrentUser()))
                            Toast.makeText(getActivity(), "请重新选择", Toast.LENGTH_SHORT).show();
                        else {
                            // start chat acitivity
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            if(conversation.isGroup()){
                                if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                                    // it's group chat
                                    intent.putExtra(Constants.EXTRA_CHAT_TYPE, Constants.CHATTYPE_CHATROOM);
                                }else{
                                    intent.putExtra(Constants.EXTRA_CHAT_TYPE, Constants.CHATTYPE_GROUP);
                                }

                            }
                            // it's single chat
                            intent.putExtra(Constants.EXTRA_USER_ID, username);
                            startActivity(intent);
                        }
                        break;
                    case R.id.delete:

                        EMConversation tobeDeleteCons = conversationLists.get(position);
                        if (tobeDeleteCons == null) {
                            return;
                        }
                        if(tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat){
                            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
                        }
                        try {
                            // delete conversation
                            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), false);

                            // To delete the native stored adked users in this conversation.
                            if (false) {
                                EaseDingMessageHelper.get().delete(tobeDeleteCons);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        refresh();
                        break;

                }
            }
        });

    }



    //设置titlebar
    public void setTitleBar(){
        title_bar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        title_bar.setLeftPadding(MacUtils.dpto(5),MacUtils.dpto(5),MacUtils.dpto(5),MacUtils.dpto(5));
        title_bar.setCenterTitle("消息");
        title_bar.setCenterColor(getResources().getColor(R.color.whilte));
//        title_bar.setCenterFontSize(MacUtils.dpto(12));
        title_bar.setCenterFontSize(18);
//        title_bar.setCenterFontStyle(1);
        title_bar.setLeftPadding(MacUtils.dpto(5),0,MacUtils.dpto(5),0);
        title_bar.setBackGroundColor(0xff0F8ED8);
        title_bar.setLeftTitle("返回");
        title_bar.setLeftColor(0xffffffff);
        title_bar.setLeftFontSize(18);
        title_bar.setLeftPadding(0,0,0,0);
        title_bar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        title_bar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
        title_bar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        title_bar.addviews();
    }

    @Override
    public void onClick(View v) {

    }

    //监听当前状态是否链接
    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();

    }

    @Override
    protected void onConnectionConnected(){

    }




}
