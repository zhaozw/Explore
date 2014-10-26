package com.explore.android.mobile.activity.out;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.activity.outline.OutLineAddActivity;
import com.explore.android.mobile.adapter.OutLineModifyExpandListAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.ActionConstants;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutDetailRequest;
import com.explore.android.mobile.model.DetailInfo;
import com.explore.android.mobile.model.ExpandListData;

/**
 * 销售明细
 * @author Ryan
 */
public class OutDetailOutLineFragment extends BaseHttpFragment {

	private static final int REQUEST_GETOUTLINE = 1;

	private RelativeLayout loading;
	private List<ExpandListData> outInfoList;
	private LinearLayout list_layout;
	private TextView tv_nodata;
	private ImageButton btn_add_outline;

	private ExpandableListView detail_listView;
	private OutLineModifyExpandListAdapter outLineAdapter;
    // 已经存在列表中的商品
    public static List<String> productIdInList;

	private String outId;

	private OutDetailActivity mActivity;

	private int preview_list_num = -1;
	private boolean flag = false;
	private ExCacheManager cacheManager;
	private boolean hasCache = false;

	@Override
	protected int setLayoutId() {
		return R.layout.frag_outinfo_detail_layout;
	}

	@Override
	public void initViews(View view) {
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		detail_listView = (ExpandableListView) view
				.findViewById(R.id.frag_outinfo_detail_exlist);
		list_layout = (LinearLayout) view
				.findViewById(R.id.frag_outinfo_detail_list_layout);
		tv_nodata = (TextView) view
				.findViewById(R.id.frag_outinfo_detail_nodata);
		btn_add_outline = (ImageButton) view
				.findViewById(R.id.frag_btn_outinfo_add_outline);

		detail_listView.setGroupIndicator(null);
	}

	@Override
	public void initValues() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ActionConstants.ACTION_OUTDETAIL_DATA_READY);

		if (getActivity() != null && getActivity() instanceof OutDetailActivity) {
			cacheManager = ExCacheManager.getInstance(getActivity());
			mActivity = (OutDetailActivity) getActivity();
			outId = mActivity.getOutId();
			outInfoList = new ArrayList<ExpandListData>();
            productIdInList = new ArrayList<String>();
			outLineAdapter = new OutLineModifyExpandListAdapter(outInfoList,
					getActivity(), this, !mActivity.hasAudit);
			detail_listView.setAdapter(outLineAdapter);
			getActivity().registerReceiver(dataReadyReceiver, intentFilter);
			if (cacheManager.getOutLineListCache(outId) != null) {
				bindOutLineData(cacheManager.getOutLineListCache(outId), true);
			}
		}
	}

	@Override
	public void initListener() {
		detail_listView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView arg0, View view,
					int position, long arg3) {
				if (preview_list_num != position) {
					detail_listView.collapseGroup(preview_list_num);
					preview_list_num = position;
					detail_listView.expandGroup(position);
					flag = false;
				} else {
					if (flag) {
						detail_listView.expandGroup(position);
						flag = false;
					} else {
						detail_listView.collapseGroup(position);
						flag = true;
					}
				}
				return true;
			}
		});

		btn_add_outline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent add_intent = new Intent(getActivity(), OutLineAddActivity.class);
				add_intent.putExtra("OUTID", outId);
				add_intent.putExtra("SOPRICELOGONID", mActivity.getSoPriceLogonId());
				add_intent.putExtra("CUSTOMERID", mActivity.getShipTo());
				startActivity(add_intent);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (AppStatus.OUTLINE_DATA_ADD) {
			loadOutInfoDetail();
		}
		AppStatus.OUTLINE_DATA_ADD = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(dataReadyReceiver);
	}

	@Override
	public void handlerResponse(String response, int what) {
		if (REQUEST_GETOUTLINE == what) {
			bindOutLineData(response, false);
		}
	}

	@Override
	public void showLoading() {
		loading.setVisibility(View.VISIBLE);

	}

	@Override
	public void dismissLoading() {
		loading.setVisibility(View.GONE);
	}

	public void loadOutInfoDetail() {
		OutDetailRequest request = new OutDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setOutId(outId);
		asynDataRequest(RequestConstants.OUTLINE_GET_LIST, request.toJsonString(), REQUEST_GETOUTLINE);
	}
	
	public void signalForUpdate() {
		mActivity.updateOutInfo();
	}

	private void bindOutLineData(String dataStr, boolean isFromCache) {
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(jsonObject
					.get(ResponseConstant.STATUS))) {
				JSONArray jsonArray = jsonObject.getJSONArray("OUTLINE1S");
				outInfoList.clear();
                productIdInList.clear();

				List<String> keyList = new ArrayList<String>();
				keyList.add("product_partno");
				keyList.add("product_npartno");
				keyList.add("product_productName");
				keyList.add("vatRate");
				keyList.add("outQty1S");
				keyList.add("outQty1B");
				keyList.add("soPriceS");
				keyList.add("soNoTaxPriceS");
				keyList.add("soPriceB");
				keyList.add("soNoTaxPriceB");
				keyList.add("productId");
				keyList.add("soPriceSTemp");
				keyList.add("outLine1Id");

				for (int i = 0; i < jsonArray.length(); i++) {
					ExpandListData outInfo = new ExpandListData();
					List<DetailInfo> infoList = new ArrayList<DetailInfo>();

					JSONObject json = jsonArray.getJSONObject(i);

					for (int j = 0; j < keyList.size(); j++) {
						String key = keyList.get(j);
						if ("product_productName".equals(key)) {
							outInfo.setTitle1(json.getString(key));

						} else if ("outQty1S".equals(key)) {
							outInfo.setTitle2(json.getString(key));

						} else if ("soPriceSTemp".equals(key)) {
							outInfo.setTitle3(json.getString(key));

						} else if ("product_npartno".equals(key)) {
							outInfo.setTitle4(json.getString(key));

						} else if ("outLine1Id".equals(key)) {
							outInfo.setId(json.getString(key));

						} else if ("productId".equals(key)) {
							outInfo.setId2(json.getString(key));
						}
						DetailInfo info = new DetailInfo();
						int strId = getResourceIdByKey(key);
						info.setContent(json.getString(key));
						if (ExStringUtil.isResNoBlank(info.getContent())
								&& strId != R.string.app_null) {
							info.setTitle(key);
							info.setResourceId(getResourceIdByKey(key));
							infoList.add(info);
						}
					}
					outInfo.setDetailList(infoList);
					outInfoList.add(outInfo);
                    productIdInList.add(outInfo.getId2());
				}

				outLineAdapter.notifyDataSetChanged();

				if (outInfoList.size() > 0) {
					list_layout.setVisibility(View.VISIBLE);
					tv_nodata.setVisibility(View.GONE);
				} else {
					list_layout.setVisibility(View.GONE);
					tv_nodata.setVisibility(View.VISIBLE);
				}

				if (!isFromCache) {
					cacheManager.addOutLineListCache(dataStr, outId);
				}
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			showToast(R.string.msg_response_failed);
		}

	}

	private BroadcastReceiver dataReadyReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			if (intent.getAction().equals(
					ActionConstants.ACTION_OUTDETAIL_DATA_READY)) {
				mHandler.sendEmptyMessage(1);
			} else if (intent.getAction().equals(
					ActionConstants.ACTION_OUTLINE1_DATA_UPDATE)) {
				mHandler.sendEmptyMessage(1);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (!hasCache) {
					loadOutInfoDetail();
				}
				break;
			default:
				break;
			}
		}
	};

	private int getResourceIdByKey(String name) {

		if ("product_partno".equals(name)) {
			return R.string.outinfo_detail_product_partno;

		} else if ("product_npartno".equals(name)) {
			return R.string.outinfo_detail_product_npartno;

		} else if ("product_productName".equals(name)) {
			return R.string.outinfo_detail_product_productname;

		} else if ("soPriceS".equals(name)) {
			return R.string.outinfo_detail_soprices;

		} else if ("soPriceSTemp".equals(name)) {
			return R.string.app_null;

		} else if ("soPriceB".equals(name)) {
			return R.string.outinfo_detail_sopriceb;

		} else if ("soNoTaxPriceS".equals(name)) {
			return R.string.outinfo_detail_sonotaxprices;

		} else if ("soNoTaxPriceB".equals(name)) {
			return R.string.outinfo_detail_sonotaxpriceb;

		} else if ("outQty1S".equals(name)) {
			return R.string.outinfo_detail_outQty1s;

		} else if ("outQty1B".equals(name)) {
			return R.string.outinfo_detail_outQty1b;

		} else if ("productId".equals(name)) {
			return R.string.app_null;

		} else if ("outLine1Id".equals(name)) {
			return R.string.app_null;

		} else if ("vatRate".equals(name)) {
			return R.string.outinfo_detail_vatRate;

		} else {
			return R.string.outinfo_detail_info;
		}
	}

}
