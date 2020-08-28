package com.s.shenghuang.mlqm.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EMLog;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.activity.VideoCallActivity;
import com.s.shenghuang.mlqm.activity.VoiceCallActivity;
import com.s.shenghuang.mlqm.interfaces.Constants;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-24 下午2:28
 * @email:1966287146@qq.com
 */
public class ChatFragment extends EaseChatFragment  implements EaseChatFragment.EaseChatFragmentHelper {

    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        super.setUpView();
    }

    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();
        if(chatType == Constants.CHATTYPE_SINGLE){
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }
    }

    @Override
    protected void onConversationInit() {
        super.onConversationInit();
    }

    @Override
    protected void onMessageListInit() {
        super.onMessageListInit();
    }

    @Override
    protected void sendTextMessage(String content) {
        super.sendTextMessage(content);
    }

    @Override
    protected void sendBigExpressionMessage(String name, String identityCode) {
        super.sendBigExpressionMessage(name, identityCode);
    }

    @Override
    protected void sendVoiceMessage(String filePath, int length) {
        super.sendVoiceMessage(filePath, length);
    }

    @Override
    protected void sendImageMessage(String imagePath) {
        super.sendImageMessage(imagePath);
    }

    @Override
    protected void sendImageMessage(Uri imageUri) {
        super.sendImageMessage(imageUri);
    }

    @Override
    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        super.sendLocationMessage(latitude, longitude, locationAddress);
    }

    @Override
    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        super.sendVideoMessage(videoPath, thumbPath, videoLength);
    }

    @Override
    protected void sendVideoMessage(Uri videoUri, String thumbPath, int videoLength) {
        super.sendVideoMessage(videoUri, thumbPath, videoLength);
    }

    @Override
    protected void sendFileMessage(String filePath) {
        super.sendFileMessage(filePath);
    }

    @Override
    protected void sendFileMessage(Uri fileUri) {
        super.sendFileMessage(fileUri);
    }

    @Override
    protected void sendMessage(EMMessage message) {
        super.sendMessage(message);
    }

    @Override
    protected void sendPicByUri(Uri selectedImage) {
        super.sendPicByUri(selectedImage);
    }

    @Override
    protected void sendFileByUri(Uri uri) {
        super.sendFileByUri(uri);
    }

    @Override
    protected void selectPicFromCamera() {
        super.selectPicFromCamera();
    }

    @Override
    protected void selectPicFromLocal() {
        super.selectPicFromLocal();
    }


    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                startVideoCall();
                break;
            default:
                break;
        }
        //keep exist extend menu
        return false;

    }


    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            EMLog.i(TAG, "Intent to the ding-msg send activity.");
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

}
