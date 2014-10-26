package com.explore.android.mobile.activity.out;

import android.view.View;
import android.widget.LinearLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.mobile.data.request.OutDTCCreateRequest;
import com.explore.android.mobile.view.TitleTextView;

public class OutCreateDtcInfoFragment extends BaseHttpFragment{

	private LinearLayout layout_transportInfo;
	private OutCreateDtcDetailActivity activity;
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_out_createdtc_transinfo;
	}

	@Override
	public void initViews(View view) {
		layout_transportInfo = (LinearLayout) view.findViewById(R.id.frag_out_createdtc_info_layout);
	}

	@Override
	public void initValues() {
		if(getActivity() != null && getActivity() instanceof OutCreateDtcDetailActivity){
			activity = (OutCreateDtcDetailActivity) getActivity();
			getInfoData();
		}
	}

	@Override
	public void initListener() {
	}

	@Override
	public void showLoading() {
	}

	@Override
	public void dismissLoading() {
	}

	@Override
	public void handlerResponse(String response, int what) {
	}
	
	private void getInfoData(){
		OutDTCCreateRequest request = activity.request;
		layout_transportInfo.addView(getTitleText(R.string.out_transportcode1, request.getTransportCode1()));
		layout_transportInfo.addView(getTitleText(R.string.out_cusdept, request.getCustomerDept()));
		layout_transportInfo.addView(getTitleText(R.string.out_sopricelogon, request.getSoPriceLogon()));
		layout_transportInfo.addView(getTitleText(R.string.out_shipto2, request.getShipTo()));
		layout_transportInfo.addView(getTitleText(R.string.out_vatto, request.getVatTo()));
		layout_transportInfo.addView(getTitleText(R.string.out_shipto_adress, request.getShipToAdress()));
		layout_transportInfo.addView(getTitleText(R.string.out_outtime, request.getOutTime()));
	}
	
	private TitleTextView getTitleText(int title, String content){
		TitleTextView titleTextView = new TitleTextView(getActivity(), null);
		titleTextView.setTitle(title);
		titleTextView.setContent(content);
		return titleTextView;
	}

}
