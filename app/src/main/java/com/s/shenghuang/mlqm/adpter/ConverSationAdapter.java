package com.s.shenghuang.mlqm.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.util.DateUtils;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.utils.EaseUserUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-06-29
 * @params 会话列表
 **/
public class ConverSationAdapter extends BaseQuickAdapter<EMConversation, BaseViewHolder> {

    private Context context;

    public ConverSationAdapter(int layoutResId, @Nullable List<EMConversation> data) {
        super(layoutResId, data);
    }

    public ConverSationAdapter(Context context, @Nullable List<EMConversation> data) {
        super(R.layout.adpter_conversation,data);
        this.context=context;
    }

    public ConverSationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EMConversation item) {


        // get username or group id
        String username = item.conversationId();

        if (item.getType() == EMConversation.EMConversationType.GroupChat) {
            String groupId = item.conversationId();
            if(EaseAtMessageHelper.get().hasAtMeMsg(groupId)){
                helper.setVisible(R.id.mentioned,true);
            }else{
                helper.setVisible(R.id.mentioned,false);
            }
            // group message, show group avatar
            helper.setImageResource(R.id.avatar,R.drawable.ease_group_icon);
            EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
            helper.setText(R.id.name,group != null ? group.getGroupName() : username);
        } else if(item.getType() == EMConversation.EMConversationType.ChatRoom){
            helper.setImageResource(R.id.avatar,R.drawable.ease_group_icon);
            EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
            helper.setText(R.id.name,room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : username);
            helper.setVisible(R.id.mentioned,false);
        }else {

            EaseUserUtils.setUserAvatar(context, username, (ImageView) helper.getView(R.id.avatar));
//            EaseUserUtils.setUserAvatar(contexts, AppSPUtils.getValueFromPrefrences(contexts,"logoUrl",""), holder.avatar);
            EaseUserUtils.setUserNick(context,username, (TextView) helper.getView(R.id.name));
            helper.setVisible(R.id.mentioned,false);
        }


        if (item.getUnreadMsgCount() > 0) {
            // show unread message count
            helper.setText(R.id.unread_msg_number,String.valueOf(item.getUnreadMsgCount()));
            helper.setVisible(R.id.unread_msg_number,true);
        } else {
            helper.setVisible(R.id.unread_msg_number,false);
        }

        if (item.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = item.getLastMessage();
            String content = null;
            helper.setText(R.id.message, EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, (context))));
            if(content != null){
                helper.setText(R.id.message,content);
            }

           helper.setText(R.id.times, DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
            if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                helper.setVisible(R.id.times,false);
            } else {
                helper.setVisible(R.id.times,true);
            }
        }
        helper.addOnClickListener(R.id.list_itease_layout);
        helper.addOnClickListener(R.id.delete);


    }
    private EaseConversationListHelper conversationListHelper;


    public interface EaseConversationListHelper{
        /**
         * set content of second line
         * @param lastMessage
         * @return
         */
        String onSetItemSecondaryText(EMMessage lastMessage);
    }
}
