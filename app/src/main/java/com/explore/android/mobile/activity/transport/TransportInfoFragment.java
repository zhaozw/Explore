package com.explore.android.mobile.activity.transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.http.VolleyHelper;
import com.explore.android.core.util.DateUtil;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.data.request.TransportDetailRequest;
import com.explore.android.mobile.model.DetailInfo;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.PullMode;

public class TransportInfoFragment extends BaseHttpFragment implements OnHeaderRefreshListener{

    private static final int TRANSPORT_INFO = 1;

	private String transportId;
	private ListView listView;
	private RelativeLayout loading;
	private PullToRefreshView mPullToRefreshView;

	private DetailInfoAdapter adapter;
	private List<DetailInfo> detailList;
	private TransportDetailActivity activity;

	@Override
	protected int setLayoutId() {
		return R.layout.frag_detail_list;
	}

	@Override
	public void initViews(View view) {
		listView = (ListView) view.findViewById(R.id.frag_detail_list);
		listView.setEmptyView(view.findViewById(android.R.id.empty));
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.frag_search_pullrefresh_view);
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		mPullToRefreshView.setPullMode(PullMode.NOFOOT);
	}

	@Override
	public void initValues() {
		if (getActivity() != null && getActivity() instanceof TransportDetailActivity){
			activity = (TransportDetailActivity) getActivity();
			transportId = activity.transportId;
			detailList = new ArrayList<DetailInfo>();
			adapter = new DetailInfoAdapter(getActivity(), detailList);
			listView.setAdapter(adapter);
			mPullToRefreshView.setOnHeaderRefreshListener(this);
			//loadTransportDetail();
			
			if (activity.responseInfo == null) {
				loadTransportInfo();	
			} else {
				bindTransportDetail(activity.responseInfo);
			}
		}
	}

	@Override
	public void initListener() {
	}

	@Override
	public void handlerResponse(String response, int what) {
        if (TRANSPORT_INFO == what) {
        }
	}

	@Override
	public void showLoading() {
		return;
	}

	@Override
	public void dismissLoading() {
		return;
	}

	private void loadTransportInfo() {
		loading.setVisibility(View.VISIBLE);
		TransportDetailRequest request = new TransportDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setTransportId(transportId);
		VolleyHelper volley = new VolleyHelper(getActivity());
		volley.setOnResponseListener(new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				loading.setVisibility(View.GONE);
				activity.responseInfo = response;
				bindTransportDetail(response);
				mPullToRefreshView.onHeaderRefreshComplete(DateUtil.dateFormatWithDayTime(new Date()));
			}
		});
		volley.setOnErrorListener(new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				loading.setVisibility(View.GONE);
				activity.responseInfo = null;
				mPullToRefreshView.onHeaderRefreshComplete(getString(R.string.msg_last_request_failed));
			}
		});
		volley.httpRequest(getPreferences().getHttpUrl(), RequestConstants.TRANSPORT_DETAIL, request.toJsonString());
	}

    private void loadTransportInfo2() {
        loading.setVisibility(View.VISIBLE);
        TransportDetailRequest request = new TransportDetailRequest();
        request.setUserId(getPreferences().getUserId());
        request.setToken(getPreferences().getTokenLast8());
        request.setTransportId(transportId);
        asynDataRequest(RequestConstants.STOCK_DETAIL, request.toJsonString(), TRANSPORT_INFO);
    }

	private void bindTransportDetail(JSONObject jsonObject) {
		try {
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				JSONObject json = jsonObject.getJSONObject("TRANSPORT");

				List<String> keyList = new ArrayList<String>();
				keyList.add("transportId");
				keyList.add("transportCode1");
				keyList.add("transportCode2");
				keyList.add("outCode1");
				keyList.add("outCode2");
				keyList.add("outType");
				keyList.add("customerDept");
				keyList.add("project");
				keyList.add("vendor");
				keyList.add("warehouse");
				keyList.add("warehouseTo");
				keyList.add("transportTime");
				keyList.add("shipTo");
				keyList.add("vatTo");
				keyList.add("transportQty1");
				keyList.add("transportMoney1");
				keyList.add("transportNoTaxMoney1");
				keyList.add("transportQty2");
				keyList.add("transportMoney2");
				keyList.add("transportNoTaxMoney2");
				keyList.add("isDd");
				keyList.add("sts");
				keyList.add("remarks");
				keyList.add("signByName");
				keyList.add("signByTime");
				keyList.add("createByName");
				keyList.add("createByTime");
				keyList.add("commitByName");
				keyList.add("commitByTime");

				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					DetailInfo info = new DetailInfo();
					int strId = getResourceIdByKey(key);
					if ("isDd".equals(key)) {
						info.setContent(getResources()
								.getString(
										ValueToResource.getYNValue(json
												.getString(key))));
					} else {
						info.setContent(json.getString(key));
					}
					if (ExStringUtil.isResNoBlank(info.getContent())
							&& strId != R.string.app_null) {
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key));
						detailList.add(info);
					}
				}

				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private int getResourceIdByKey(String name) {

		if ("transportCode1".equals(name)) {
			return R.string.transdetail_transportcode1;

		} else if ("transportCode2".equals(name)) {
			return R.string.transdetail_transportcode2;

		} else if ("transportId".equals(name)) {
			return R.string.app_null;

		} else if ("outType".equals(name)) {
			return R.string.transdetail_outtype;

		} else if ("customerDept".equals(name)) {
			return R.string.transdetail_customerdept;

		} else if ("project".equals(name)) {
			return R.string.transdetail_project;

		} else if ("vendor".equals(name)) {
			return R.string.transdetail_vendor;

		} else if ("vatTo".equals(name)) {
			return R.string.transdetail_vatTo;

		} else if ("warehouse".equals(name)) {
			return R.string.transdetail_warehouse;

		} else if ("transportTime".equals(name)) {
			return R.string.transdetail_transporttime;

		} else if ("createByName".equals(name)) {
			return R.string.transdetail_createbyname;

		} else if ("createByTime".equals(name)) {
			return R.string.transdetail_createbytime;

		} else if ("commitByName".equals(name)) {
			return R.string.transdetail_commitbyname;

		} else if ("commitByTime".equals(name)) {
			return R.string.transdetail_commitbytime;

		} else if ("outCode1".equals(name)) {
			return R.string.transdetail_outcode1;

		} else if ("outCode2".equals(name)) {
			return R.string.transdetail_outcode2;

		} else if ("shipTo".equals(name)) {
			return R.string.transdetail_shipto;

		} else if ("warehouseTo".equals(name)) {
			return R.string.transdetail_warehouseto;

		} else if ("transportQty1".equals(name)) {
			return R.string.transdetail_transportqty1;

		} else if ("transportMoney1".equals(name)) {
			return R.string.transdetail_transportmoney1;

		} else if ("transportNoTaxMoney1".equals(name)) {
			return R.string.transdetail_transportnotaxmoney1;

		} else if ("transportQty2".equals(name)) {
			return R.string.transdetail_transportqty2;

		} else if ("transportMoney2".equals(name)) {
			return R.string.transdetail_transportmoney2;

		} else if ("transportNoTaxMoney2".equals(name)) {
			return R.string.transdetail_transportnotaxmoney2;

		} else if ("sts".equals(name)) {
			return R.string.transdetail_sts;

		} else if ("isDd".equals(name)) {
			return R.string.transdetail_isdd;

		} else if ("remarks".equals(name)) {
			return R.string.transdetail_remarks;

		} else if ("signByName".equals(name)) {
			return R.string.transdetail_signbyname;

		} else if ("signByTime".equals(name)) {
			return R.string.transdetail_signbytime;

		} else {
			return R.string.transdetail_title;
		}
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadTransportInfo();
			}
		}, 300);
	}

}
