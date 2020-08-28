package com.s.shenghuang.mlqm.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.demoss.BaseConfig;
import com.s.shenghuang.mlqm.rxbus.RxBus;
import com.s.shenghuang.mlqm.rxbus.event.WechatPaySuccessEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private TextView resultTv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxchat_pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, BaseConfig.WXCHAT_APPID);
        api.handleIntent(getIntent(), this);

		resultTv =findViewById(R.id.result_tv);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			switch (resp.errCode) {
				case BaseResp.ErrCode.ERR_OK:
					RxBus.getDefault().post(new WechatPaySuccessEvent("success"));
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					RxBus.getDefault().post(new WechatPaySuccessEvent("cancel"));
					break;
				default:
					RxBus.getDefault().post(new WechatPaySuccessEvent("fail"));
					break;
			}
			finish();
		}
	}
}