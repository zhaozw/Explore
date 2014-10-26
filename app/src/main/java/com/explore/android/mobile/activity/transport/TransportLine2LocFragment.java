package com.explore.android.mobile.activity.transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import com.explore.android.mobile.model.TransportLineLoc;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.PullState;
import com.google.gson.Gson;

public class TransportLine2LocFragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {

	private String transportId;
	private GridView locView;
	private RelativeLayout loading;
	private PullToRefreshView mPullToRefreshView;
	private TextView emptyView;
	private List<TransportLineLoc> locList;
	private TransportLine2LocAdapter adapter;

	private TransportDetailActivity activity;
	private PullState state;

	@Override
	protected int setLayoutId() {
		return R.layout.frag_detail_gridlist;
	}

	@Override
	public void initViews(View view) {
		locView = (GridView) view.findViewById(R.id.frag_detail_gridview);
		emptyView = (TextView) view
				.findViewById(R.id.frag_detail_gridview_empty);
		locView.setEmptyView(emptyView);
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.frag_search_pullrefresh_gridview);
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
	}

	@Override
	public void initValues() {
		if (getActivity() != null
				&& getActivity() instanceof TransportDetailActivity) {
			activity = (TransportDetailActivity) getActivity();
			transportId = activity.transportId;
			mPullToRefreshView.setOnHeaderRefreshListener(this);
			mPullToRefreshView.setOnFooterRefreshListener(this);

			locList = new ArrayList<TransportLineLoc>();
			adapter = new TransportLine2LocAdapter();
			locView.setAdapter(adapter);

			if (activity.responseLine2 == null) {
				loadTransportLine2();
			} else {
				bindTransportLine1Data(activity.responseLine2);
			}
		}
	}

	@Override
	public void initListener() {
		emptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadTransportLine2();
			}
		});
		
		locView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Intent intent = new Intent(getActivity(), TransportLine2Activity.class);
				intent.putExtra("LOC", locList.get(position).getLOC());
				intent.putExtra("TRANSPORTID", transportId);
				startActivity(intent);
			}
		});
	}

	private void loadTransportLine2() {
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
				activity.responseLine2 = response;
				Log.e("transportLoc2Data", "data:" + response.toString());
				bindTransportLine1Data(response);
				if (PullState.HEAD == state) {
					mPullToRefreshView.onHeaderRefreshComplete(DateUtil
							.dateFormatWithDayTime(new Date()));
				} else if (PullState.FOOT == state) {
					mPullToRefreshView.onFooterRefreshComplete();
				}
			}
		});
		volley.setOnErrorListener(new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				loading.setVisibility(View.GONE);
				activity.responseLine2 = null;
				if (PullState.HEAD == state) {
					mPullToRefreshView
							.onHeaderRefreshComplete(getString(R.string.msg_last_request_failed));
				} else if (PullState.FOOT == state) {
					mPullToRefreshView.onFooterRefreshComplete();
				}
			}
		});
		volley.httpRequest(getPreferences().getHttpUrl(),
				RequestConstants.TRANSPORT_TRANSPORTLINE2LOC,
				request.toJsonString());
	}

	private void bindTransportLine1Data(JSONObject jsonObject) {
		try {
			if (ResponseConstant.OK.equals(jsonObject
					.get(ResponseConstant.STATUS))) {
				JSONArray array = jsonObject.getJSONArray("TRANSPORTLINE2LOCS");
				int length = array.length();
				Gson gson = new Gson();
				for (int i = 0; i < length; i++) {
					locList.add(gson.fromJson(array.getString(i),
							TransportLineLoc.class));
				}
				adapter.notifyDataSetChanged();
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
				loadTransportLine2();
				locList.clear();
			}
		}, 300);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				state = PullState.FOOT;
				loadTransportLine2();
			}
		}, 300);
	}

	final class TransportLine2LocAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public TransportLine2LocAdapter() {
			inflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return locList.size();
		}

		@Override
		public Object getItem(int position) {
			return locList.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = inflater
						.inflate(R.layout.gridview_transline2_item, null);
			}
			((TextView) view.findViewById(R.id.gridview_tv_item_loc))
					.setText(locList.get(position).getLOC());
			((TextView) view.findViewById(R.id.gridview_tv_item_info1))
					.setText(locList.get(position).getPRODUCTCOUNT());
			((TextView) view.findViewById(R.id.gridview_tv_item_info2))
					.setText(locList.get(position).getTRANSPORQTYSUM());

			return view;
		}

	}

}
