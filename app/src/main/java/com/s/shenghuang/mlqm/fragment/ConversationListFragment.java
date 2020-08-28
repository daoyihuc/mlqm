package com.s.shenghuang.mlqm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.ConverSationAdapter;
import com.s.shenghuang.mlqm.base.Basefragment;
import com.s.shenghuang.mlqm.view.SwipeItemLayout;
import com.s.shenghuang.mlqm.view.Titlabar;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-06-29
 * @params
 **/
public class ConversationListFragment extends Basefragment {
    private final static int MSG_REFRESH = 2;
    protected  View view;
    protected View view_status_bar;
    protected Titlabar title_bar;
    protected FrameLayout errorItemContainer;
    protected RecyclerView ConverSatation_list;
    protected boolean isConflict;
    protected boolean hidden;
    protected List<EMConversation> conversationLists = new ArrayList<EMConversation>();
    protected ConverSationAdapter converSationAdapter;


    protected EMConversationListener convListener = new EMConversationListener(){

        @Override
        public void onCoversationUpdate() {
            refresh();
        }

    };

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view= inflater.inflate(R.layout.fragment_conversation_list, container, false);
//        return view;
//    }

    @Override
    public View loadView() {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_conversation_list,null);
        return view;
    }

    @Override
    public void initData()
    {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setUpView();

    }

    protected void initView() {

        view_status_bar = getView().findViewById(R.id.view_status_bar);
        title_bar=getView().findViewById(R.id.title_bar);
        errorItemContainer = (FrameLayout) getView().findViewById(R.id.fl_error_item);
        ConverSatation_list= getView().findViewById(R.id.conversation_list);

        conversationLists.addAll(loadConversationList());
    }

    protected void setUpView() {

        EMClient.getInstance().addConnectionListener(connectionListener);
        WrapContentLinearLayoutManager conversation_manager=new WrapContentLinearLayoutManager(getActivity());
        conversation_manager.setOrientation(LinearLayoutManager.VERTICAL);
        ConverSatation_list.setLayoutManager(conversation_manager);
        converSationAdapter=new ConverSationAdapter(getActivity(),conversationLists);
        ConverSatation_list.setAdapter(converSationAdapter);
        ConverSatation_list.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));


    }

    /**
     * refresh ui
     */
    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }


    /**
     * connected to server
     */
    protected void onConnectionConnected(){
        errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected(){
        errorItemContainer.setVisibility(View.VISIBLE);
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    protected Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;

                case MSG_REFRESH:
                {
                    conversationLists.clear();
                    conversationLists.addAll(loadConversationList());
                    converSationAdapter.notifyDataSetChanged();
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * load conversation list
     *
     * @return
    +    */
    protected List<EMConversation> loadConversationList(){
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }
}
