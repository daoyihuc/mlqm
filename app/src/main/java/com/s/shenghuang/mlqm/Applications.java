package com.s.shenghuang.mlqm;

import android.app.Application;
import android.content.Context;

import com.s.shenghuang.mlqm.demoss.DemoHelper;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author:"道翼(yanwen)"
 * @params:"代理升级"
 * @data:20-8-22 下午1:26
 * @email:1966287146@qq.com
 */
public class Applications extends Application {

    public static Applications mContext;
    public static  Applications instance() {
        return mContext;
    }
    public Applications() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        MacUtils.init(this);

        DemoHelper.getInstance().init(mContext);

        //buugly
        CrashReport.initCrashReport(getApplicationContext(), "871a0541b3", false);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator(){
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout){
                //layout.setPrimaryColorsId(android.R.color.white, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setTextSizeTime(10).setTextSizeTitle(14).setDrawableArrowSize(14).setDrawableSize(15);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator(){
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout){
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setTextSizeTitle(14).setDrawableSize(15);
            }
        });
    }
}
