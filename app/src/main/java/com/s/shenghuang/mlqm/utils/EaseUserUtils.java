package com.s.shenghuang.mlqm.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.HanziToPinyin;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.demoss.db.DbOpenHelper;
import com.s.shenghuang.mlqm.demoss.db.UserDao;


import java.util.Hashtable;
import java.util.Map;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
        Map<String, EaseUser> robotList = getContactList(context);

        EaseUser user=null;
        if(robotList!=null){
            user =  robotList.get(username);
        }else{
            user=getUserInfo(username);
        }
        RequestOptions requestOptions=new RequestOptions();
//        requestOptions.transform(new GlideRoundTransform(con,MacUtils.dpto(15)));
        requestOptions.placeholder(R.drawable.man)
                .error(R.drawable.man)
                .fallback(R.drawable.man)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(100,100)
                .dontAnimate();
        RequestOptions requestOptions1 = requestOptions.circleCropTransform();



//        EaseUser user =  getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            try {

                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId)
                        .apply(requestOptions1)
                        .into(imageView);
            } catch (Exception e) {
                //use default avatar

//                .apply(RequestOptions.placeholderOf(R.drawable.ease_default_avatar)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL))

                Glide.with(context).load(user.getAvatar())
                        .apply(requestOptions1)
                        .into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.man)
                    .apply(requestOptions1)
                    .into(imageView);
        }
    }
    
    /**
     * set user's nickname
     */
    public static void setUserNick(Context context,String username,TextView textView){
        if(textView != null){
            Map<String, EaseUser> robotList = getRobotList(context);

            EaseUser user=null;
            if(robotList!=null){
                user =  robotList.get(username);
            }else{
                user=getUserInfo(username);
            }

//        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNickname() != null){
        		textView.setText(user.getNickname());
        	}else{
        		textView.setText(username);
        	}
        }
    }

    //获取数据库信息
    public static final String ROBOT_TABLE_NAME = "robots";
    public static final String ROBOT_COLUMN_NAME_ID = "username";
    public static final String ROBOT_COLUMN_NAME_NICK = "nick";
    public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";



    synchronized public static Map<String, EaseUser> getRobotList(Context context) {
        DbOpenHelper instance = DbOpenHelper.getInstance(context);
        SQLiteDatabase db = instance.getReadableDatabase();
        Map<String, EaseUser> users = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + ROBOT_TABLE_NAME, null);
            if(cursor.getCount()>0){
                users = new Hashtable<String, EaseUser>();
                Log.e("sqlite","数据承载中");
            }
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(ROBOT_COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(ROBOT_COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(ROBOT_COLUMN_NAME_AVATAR));
                EaseUser user = new EaseUser(username);
                user.setNickname(nick);
                user.setAvatar(avatar);
                String headerName = null;
                if (!TextUtils.isEmpty(user.getNickname())) {
                    headerName = user.getNickname();
                } else {
                    headerName = user.getUsername();
                }
                if(Character.isDigit(headerName.charAt(0))){
                    user.setInitialLetter("#");
                }else{
                    user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target
                            .substring(0, 1).toUpperCase());
                    char header = user.getInitialLetter().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setInitialLetter("#");
                    }
                }

                try {
                    users.put(username, user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            Log.e("sqlite","数据承载中");
            cursor.close();
        }
        return users;
    }

    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String CHAT_ROBOT = "item_robots";

    synchronized public static  Map<String, EaseUser> getContactList(Context context) {
        DbOpenHelper instance = DbOpenHelper.getInstance(context);
        SQLiteDatabase db = instance.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
                EaseUser user = new EaseUser(username);
                user.setNickname(nick);
                user.setAvatar(avatar);
                if (username.equals(NEW_FRIENDS_USERNAME) || username.equals(GROUP_USERNAME)
                        || username.equals(CHAT_ROOM)|| username.equals(CHAT_ROBOT)) {
                    user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                users.put(username, user);
                Log.e("sqlite","数据承载中2");
            }
            cursor.close();
            Log.e("sqlite","数据承载中1");
        }
        return users;
    }
}
