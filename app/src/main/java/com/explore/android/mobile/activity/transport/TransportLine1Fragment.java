package com.explore.android.mobile.activity.transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.explore.android.R;
import com.explore.android.core.base.BaseFragment;
import com.explore.android.core.http.VolleyHelper;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.TransportDetailRequest;
import com.explore.android.mobile.model.TransportLine1;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.PullState;
import com.google.gson.Gson;

public class TransportLine1Fragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {

	private String transportId;
	private ListView listView;
	private RelativeLayout loading;
	private PullToRefreshView mPullToRefreshView;
	private TextView emptyView;
	private List<TransportLine1> transportLine1List;
	private TransportLine1Adapter adapter;

	private TransportDetailActivity activity;
	private static int page_num = 1;
	private PullState state;

	@Override
	protected int setLayoutId() {
		return R.layout.frag_detail_list;
	}

	@Override
	public void initViews(View view) {
		listView = (ListView) view.findViewById(R.id.frag_detail_list);
		emptyView = (TextView) view.findViewById(R.id.frag_detail_list_empty);
		listView.setEmptyView(emptyView);
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.frag_search_pullrefresh_view);
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
	}

	@Override
	public void initValues() {
		page_num = 1;
		if (getActivity() != null
				&& getActivity() instanceof TransportDetailActivity) {
			activity = (TransportDetailActivity) getActivity();
			transportId = activity.transportId;
			mPullToRefreshView.setOnHeaderRefreshListener(this);
			mPullToRefreshView.setOnFooterRefreshListener(this);

			transportLine1List = new ArrayList<TransportLine1>();
			adapter = new TransportLine1Adapter();
			listView.setAdapter(adapter);

			if (activity.responseLine1 == null) {
				loadTransportLine1();
			} else {
				bindTransportLine1Data(activity.responseLine1);
			}
		}
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

			}
		});

		emptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				transportLine1List.clear();
				loadTransportLine1();
			}
		});
	}

	private void loadTransportLine1() {
		loading.setVisibility(View.VISIBLE);
		TransportDetailRequest request = new TransportDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setTransportId(transportId);
		request.setPageNum(page_num);
		request.setPsize(RequestConstants.SEARCH_PAGE_SIZE);
		VolleyHelper volley = new VolleyHelper(getActivity());
		volley.setOnResponseListener(new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				loading.setVisibility(View.GONE);
				bindTransportLine1Data(response);
				activity.responseLine1 = response;
				page_num++;
				if (PullState.HEAD == state) {
					mPullToRefreshView.onHeaderRefreshComplete(DateUtil
							.dateFormatWithDayTime(new Date()));
				} else if (PullState.FOOT == state) {
					mPullToRefreshView.onFooterRefreshComplete();
				}
				adapter.notifyDataSetChanged();
			}
		});
		volley.setOnErrorListener(new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				loading.setVisibility(View.GONE);
				activity.responseLine1 = null;
				if (PullState.HEAD == state) {
					mPullToRefreshView
							.onHeaderRefreshComplete(getString(R.string.msg_last_request_failed));
				} else if (PullState.FOOT == state) {
					mPullToRefreshView.onFooterRefreshComplete();
				}
			}
		});
		volley.httpRequest(getPreferences().getHttpUrl(),
				RequestConstants.TRANSPORT_TRANSPORTLINE1,
				request.toJsonString());
	}

	private void bindTransportLine1Data(JSONObject jsonObject) {
		try {
			if (ResponseConstant.OK.equals(jsonObject
					.get(ResponseConstant.STATUS))) {
				JSONArray array = jsonObject.getJSONArray("TRANSPORTLINE1LIST");
				int length = array.length();
				Gson gson = new Gson();
				for (int i = 0; i < length; i++) {
					transportLine1List.add(gson.fromJson(array.getString(i),
							TransportLine1.class));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				state = PullState.HEAD;
				loadTransportLine1();
				transportLine1List.clear();
				page_num = 1;
			}
		}, 300);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				state = PullState.FOOT;
				loadTransportLine1();
			}
		}, 300);
	}

	final class TransportLine1Adapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public TransportLine1Adapter() {
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return transportLine1List.size();
		}

		@Override
		public Object getItem(int position) {
			return transportLine1List.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if (view == null) {
				view = mInflater.inflate(R.layout.list_transportline1_item,
						parent, false);
			}
			((TextView) view.findViewById(R.id.item_tranlist_code))
					.setText(transportLine1List.get(position).getPartno() + ", "
							+ transportLine1List.get(position).getNpartno());
			((TextView) view.findViewById(R.id.item_tranlist_name))
					.setText(transportLine1List.get(position).getProductName());
			((TextView) view.findViewById(R.id.item_tranlist_money))
					.setText(transportLine1List.get(position)
							.getTransportQty1B());
			return view;
		}
	}

}
